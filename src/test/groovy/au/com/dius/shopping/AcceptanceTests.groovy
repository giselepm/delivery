package au.com.dius.shopping

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class AcceptanceTests extends Specification {
    private PricingRules pricingRules
    Checkout checkout
    static Product vgaAdapter
    static Product iPad
    static Product macBookPro
    static Product appleTV

    void setup() {
        pricingRules = new PricingRules()
        checkout = new Checkout(pricingRules)
        vgaAdapter = new Product("vga", "VGA Adapter", 30.00)
        iPad = new Product("ipd", "Super iPad", 549.99)
        macBookPro = new Product("mbp", "MacBook Pro", 1399.99)
        appleTV = new Product("atv", "Apple TV", 109.50)
    }

    def "When no product was scanned, the total is \$0"() {
        expect:
        checkout.total() == "\$0.00"
    }

    def "When one #product.name is scanned, the total is \$#product.price"() {
        given:
        checkout.scan(product)

        expect:
        checkout.total() == "\$${product.price}"

        where:
        product << [vgaAdapter, iPad, macBookPro, appleTV]
    }

    def "When two #product.name are scanned, the total is 2 x #product.price"() {
        given:
        checkout.scan(product)
        checkout.scan(product)

        expect:
        checkout.total() == "\$${product.price * 2}"

        where:
        product << [vgaAdapter, iPad, macBookPro, appleTV]
    }

    def "When #appleTVs apple TVs and 1 vga adapter are scanned, the total is \$#total"() {
        given:
        appleTVs.times {
            checkout.scan(appleTV)
        }

        and:
        checkout.scan(vgaAdapter)

        expect:
        checkout.total() == "\$$total"

        where:
        appleTVs | total
        3        | appleTV.price * 2 + vgaAdapter.price
        4        | appleTV.price * 3 + vgaAdapter.price
        5        | appleTV.price * 4 + vgaAdapter.price
        6        | appleTV.price * 4 + vgaAdapter.price
        7        | appleTV.price * 5 + vgaAdapter.price
        8        | appleTV.price * 6 + vgaAdapter.price
        9        | appleTV.price * 6 + vgaAdapter.price
    }

    def "When #ipads super iPads and 2 apple TVs are scanned, the total is \$#total"() {
        given:
        checkout.scan(appleTV)

        and:
        2.times {
            checkout.scan(iPad)
        }

        and:
        checkout.scan(appleTV)

        and:
        (ipads - 2).times {
            checkout.scan(iPad)
        }

        expect:
        checkout.total() == "\$$total"

        where:
        ipads | total
        4     | appleTV.price * 2 + iPad.price * 4
        5     | appleTV.price * 2 + 499.99 * 5
        6     | appleTV.price * 2 + 499.99 * 6
    }

    def "When #macBooks mac books, #vgaAdapters vga adapters and 1 iPad are scanned, the total is \$#total"() {
        given:
        macBooks.times {
            checkout.scan(macBookPro)
        }

        and:
        vgaAdapters.times {
            checkout.scan(vgaAdapter)
        }

        and:
        checkout.scan(iPad)

        expect:
        checkout.total() == "\$$total"

        where:
        macBooks | vgaAdapters | total
        1        | 1           | macBookPro.price + iPad.price
        2        | 2           | macBookPro.price * 2 + iPad.price
        2        | 1           | macBookPro.price * 2 + iPad.price
        1        | 2           | macBookPro.price + vgaAdapter.price + iPad.price
        3        | 5           | macBookPro.price * 3 + vgaAdapter.price * 2 + iPad.price
        3        | 0           | macBookPro.price * 3 + iPad.price
        0        | 3           | vgaAdapter.price * 3 + iPad.price
        6        | 1           | macBookPro.price * 6 + iPad.price
    }
}