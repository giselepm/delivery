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
}