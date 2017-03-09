package au.com.dius.shopping

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class AcceptanceTests extends Specification {
    private PricingRules pricingRules
    Checkout checkout

    void setup() {
        pricingRules = new PricingRules()
        checkout = new Checkout(pricingRules)
    }

    def "When no product was scanned, the total is \$0"() {
        expect:
        "\$0.00" == checkout.total()
    }

    def "When one #name is scanned, the total is \$#price"() {
        when:
        Product product = new Product(sku, name, price)
        checkout.scan(product)

        then:
        "\$${price}" == checkout.total()

        where:
        sku   | name          | price
        "vga" | "VGA Adapter" | 30.00
        "ipd" | "Super iPad"  | 549.99
        "mbp" | "MacBook Pro" | 1399.99
        "atv" | "Apple TV"    | 109.50

    }
}