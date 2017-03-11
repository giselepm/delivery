package au.com.dius.shopping.pricingRules

import au.com.dius.shopping.Item
import au.com.dius.shopping.Product
import spock.lang.Specification

class SuperIPadBulkDiscountPricingRuleSpec extends Specification {
    SuperIPadBulkDiscountPricingRule rule
    Product superIPad
    Product macBook

    Item superIPadItem1
    Item superIPadItem2
    Item superIPadItem3
    Item superIPadItem4
    Item superIPadItem5
    Item superIPadItem6
    Item macBookItem

    def setup() {
        rule = new SuperIPadBulkDiscountPricingRule()

        superIPad = Mock(Product)
        macBook = Mock(Product)

        superIPad.isSuperIpad() >> true
        macBook.isSuperIpad() >> false

        superIPadItem1 = Mock(Item)
        superIPadItem2 = Mock(Item)
        superIPadItem3 = Mock(Item)
        superIPadItem4 = Mock(Item)
        superIPadItem5 = Mock(Item)
        superIPadItem6 = Mock(Item)
        macBookItem = Mock(Item)

        superIPadItem1.getProduct() >> superIPad
        superIPadItem2.getProduct() >> superIPad
        superIPadItem3.getProduct() >> superIPad
        superIPadItem4.getProduct() >> superIPad
        superIPadItem5.getProduct() >> superIPad
        superIPadItem6.getProduct() >> superIPad
        macBookItem.getProduct() >> macBook

    }

    def "isValid should return true"() {
        expect:
        rule.isValid()
    }

    def "applyRule should not change the price of any iPad if there are 4 ipads in the item list"() {
        given:
        List items = [superIPadItem1, superIPadItem2, superIPadItem3, superIPadItem4, macBookItem]

        when:
        rule.applyRule(items)

        then:
        0 * superIPadItem1.setPrice(_)
        0 * superIPadItem2.setPrice(_)
        0 * superIPadItem3.setPrice(_)
        0 * superIPadItem4.setPrice(_)
        0 * macBookItem.setPrice(_)
    }

    def "applyRule should change the price of all superIPad items if there are 5 iPads in the list"() {
        given:
        List items = [superIPadItem1, superIPadItem2, superIPadItem3, superIPadItem4, superIPadItem5, macBookItem]

        when:
        rule.applyRule(items)

        then:
        1 * superIPadItem1.setPrice(499.99)
        1 * superIPadItem2.setPrice(499.99)
        1 * superIPadItem3.setPrice(499.99)
        1 * superIPadItem4.setPrice(499.99)
        1 * superIPadItem5.setPrice(499.99)
        0 * macBookItem.setPrice(_)
    }

    def "applyRule should change the price of all superIPad items if there are 6 iPads in the list"() {
        given:
        List items = [superIPadItem1, superIPadItem2, superIPadItem3, superIPadItem4, superIPadItem5, superIPadItem6]

        when:
        rule.applyRule(items)

        then:
        1 * superIPadItem1.setPrice(499.99)
        1 * superIPadItem2.setPrice(499.99)
        1 * superIPadItem3.setPrice(499.99)
        1 * superIPadItem4.setPrice(499.99)
        1 * superIPadItem5.setPrice(499.99)
        1 * superIPadItem6.setPrice(499.99)
        0 * macBookItem.setPrice(_)
    }
}
