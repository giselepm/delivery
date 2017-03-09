package au.com.dius.shopping

import spock.lang.IgnoreRest
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
        "\$0.00" == checkout.total()
    }

    def "When one #product.name is scanned, the total is \$#product.price"() {
        when:
        checkout.scan(product)

        then:
        "\$${product.price}" == checkout.total()

        where:
        product << [vgaAdapter, iPad, macBookPro, appleTV]
    }

    def "When two #product.name are scanned, the total is 2 x #product.price"() {
        when:
        checkout.scan(product)
        checkout.scan(product)

        then:
        "\$${product.price * 2}" == checkout.total()

        where:
        product << [vgaAdapter, iPad, macBookPro, appleTV]
    }

    def "When #amount apple TVs and 1 vga adapter are scanned, the total is \$#total"() {
        when:
        amount.times {
            checkout.scan(appleTV)
        }
        checkout.scan(vgaAdapter)

        then:
        "\$$total" == checkout.total()

        where:
        amount | total
        3 | (appleTV.price*2 + vgaAdapter.price)
        4 | (appleTV.price*3 + vgaAdapter.price)
        5 | (appleTV.price*4 + vgaAdapter.price)
        6 | (appleTV.price*4 + vgaAdapter.price)
        7 | (appleTV.price*5 + vgaAdapter.price)
        8 | (appleTV.price*6 + vgaAdapter.price)
        9 | (appleTV.price*6 + vgaAdapter.price)
    }
}