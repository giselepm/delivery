package au.com.dius.shopping

import spock.lang.Specification

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

    def "When one VGA adapter is scanned, the total is \$30.00"() {
        when:
        Product vgaAdapter = new Product("vga", "VGA Adapter", 30.00)
        checkout.scan(vgaAdapter)

        then:
        "\$30.00" == checkout.total()
    }

    def "When one Super iPad is scanned, the total is \$549.00"() {
        when:
        Product ipad = new Product("ipd", "Super iPad", 549.00)
        checkout.scan(ipad)

        then:
        "\$549.00" == checkout.total()
    }
}