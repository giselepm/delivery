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
        "\$${product.price*2}" == checkout.total()

        where:
        product << [vgaAdapter, iPad, macBookPro, appleTV]
    }

    def "When 3 apple TVs and 1 vga adapter are scanned, the total is \$249.00"() {
        when:
        checkout.scan(appleTV)
        checkout.scan(appleTV)
        checkout.scan(appleTV)
        checkout.scan(vgaAdapter)

        then:
        "\$249.00" == checkout.total()
    }

    def "When 6 apple TVs are scanned, the total is the price of only four of them"() {
        when:
        checkout.scan(appleTV)
        checkout.scan(appleTV)
        checkout.scan(appleTV)
        checkout.scan(appleTV)
        checkout.scan(appleTV)
        checkout.scan(appleTV)

        then:
        "\$${appleTV.price*4}" == checkout.total()
    }
}