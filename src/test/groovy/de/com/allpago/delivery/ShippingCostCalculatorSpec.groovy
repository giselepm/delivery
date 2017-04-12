package de.com.allpago.delivery

import de.com.allpago.delivery.domain.Package
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class ShippingCostCalculatorSpec extends Specification {

    def "when a package has width=#width cm, length=#lenght cm and height=#height cm, its volumetric weight should be #weight kg"() {
        expect:
        ShippingCostCalculator.calculateVolumetricWeight(new Package(0, width, lenght, height)) == weight

        where:
        width | lenght | height | weight
        26    | 10     | 11     | 1
        1     | 1      | 1      | 0.5
        59.65 | 45.99  | 23.65  | 13
        59.65 | 45.99  | 0      | 0
        0     | 0      | 0      | 0
    }

    def "when a package has width=#width cm, length=#lenght cm, height=#height cm and weight = #weight kg, its normalized weight should be #normalizedWeight kg"() {
        expect:
        ShippingCostCalculator.calculateNormalizedWeight(new Package(weight, width, lenght, height)) == normalizedWeight

        where:
        width | lenght | height | weight | normalizedWeight
        26    | 10     | 11     | 3.6    | 3.6
        26    | 10     | 11     | 0.4    | 1
        0     | 0      | 0      | 10     | 10
    }

    def "when a package has width=#width cm, length=#lenght cm, height=#height cm and weight = #weight kg and its hard to a friend is #hard, its shipping cost should be #cost EUR"() {
        expect:
        ShippingCostCalculator.calculateShippingCost(hard, new Package(weight, width, lenght, height)) == cost

        where:
        width | lenght | height | weight | hard | cost
        26    | 10     | 11     | 0.4    | 17   | 4.12
        26    | 10     | 11     | 0.4    | 128  | 11.31
        26    | 10     | 11     | 0.4    | 156  | 12.49
        -26   | 10     | 11     | 0.4    | 156  | 12.49
        26    | -10    | 11     | 0.4    | 156  | 12.49
        26    | 10     | -11    | 0.4    | 156  | 12.49
        26    | 10     | 11     | -0.4   | 156  | 12.49
        26    | 10     | 11     | 0.4    | -156 | 12.49
        26    | 10     | 11     | 0.4    | null | null
    }

}
