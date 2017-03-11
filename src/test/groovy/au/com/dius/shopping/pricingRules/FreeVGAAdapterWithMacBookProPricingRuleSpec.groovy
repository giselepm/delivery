package au.com.dius.shopping.pricingRules

import au.com.dius.shopping.Item
import au.com.dius.shopping.Product
import spock.lang.Specification

class FreeVGAAdapterWithMacBookProPricingRuleSpec extends Specification {
    FreeVGAAdapterWithMacBookProPricingRule rule
    Product vga
    Product macBook

    Item vgaItem1
    Item vgaItem2
    Item vgaItem3
    Item vgaItem4
    Item macBookItem1
    Item macBookItem2

    def setup() {
        rule = new FreeVGAAdapterWithMacBookProPricingRule()

        vga = Mock(Product)
        macBook = Mock(Product)

        vga.isMacBookPro() >> false
        vga.isVGAAdapter() >> true
        macBook.isMacBookPro() >> true
        macBook.isVGAAdapter() >> false

        vgaItem1 = Mock(Item)
        vgaItem2 = Mock(Item)
        vgaItem3 = Mock(Item)
        vgaItem4 = Mock(Item)
        macBookItem1 = Mock(Item)
        macBookItem2 = Mock(Item)

        vgaItem1.getProduct() >> vga
        vgaItem2.getProduct() >> vga
        vgaItem3.getProduct() >> vga
        vgaItem4.getProduct() >> vga
        macBookItem1.getProduct() >> macBook
        macBookItem2.getProduct() >> macBook

    }

    def "isValid should return true"() {
        expect:
        rule.isValid()
    }

    def "applyRule should not change the price of the vgaAdapter item if there is no macBook in the list"() {
        given:
        List items = [vgaItem1, vgaItem2]

        when:
        rule.applyRule(items)

        then:
        0 * vgaItem1.bundleForFree()
        0 * vgaItem2.bundleForFree()
    }

    def "applyRule should change the price of only one vgaAdapter item if there is one macBook in the list"() {
        given:
        List items = [vgaItem1, vgaItem2, macBookItem1]

        when:
        rule.applyRule(items)

        then:
        1 * vgaItem1.bundleForFree()
        0 * vgaItem2.bundleForFree()
        0 * macBookItem1.bundleForFree()
    }

    def "applyRule should change the price of only two vgaAdapter item if there is two macBook in the list"() {
        given:
        List items = [vgaItem1, vgaItem2,
                     macBookItem1, macBookItem2,
                      vgaItem3, vgaItem4]

        when:
        rule.applyRule(items)

        then:
        1 * vgaItem1.bundleForFree()
        1 * vgaItem2.bundleForFree()
        0 * vgaItem3.bundleForFree()
        0 * vgaItem4.bundleForFree()
        0 * macBookItem1.bundleForFree()
        0 * macBookItem2.bundleForFree()
    }
}
