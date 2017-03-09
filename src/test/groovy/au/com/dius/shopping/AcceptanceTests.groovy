package au.com.dius.shopping

import spock.lang.Specification

class AcceptanceTests extends Specification {

    def "When no product was scanned, the total is \$0"() {
        when:
        PricingRules pricingRules = new PricingRules()
        Checkout co = new Checkout(pricingRules)

        then:
        "\$0.00" == co.total()
    }

    def "When one VGA adapter is scanned, the total is \$30.00"() {
        when:
        PricingRules pricingRules = new PricingRules()
        Checkout co = new Checkout(pricingRules)
        Product vgaAdapter = new Product("vga", "VGA Adapter", 30.00)
        co.scan(vgaAdapter)

        then:
        "\$30.00" == co.total()
    }
}