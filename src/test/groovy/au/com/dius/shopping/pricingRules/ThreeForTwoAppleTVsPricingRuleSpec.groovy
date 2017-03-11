package au.com.dius.shopping.pricingRules

import au.com.dius.shopping.Item
import au.com.dius.shopping.Product
import spock.lang.Specification

class ThreeForTwoAppleTVsPricingRuleSpec extends Specification {
    ThreeForTwoAppleTVsPricingRule rule
    Product appleTV
    Product macBook

    Item appleTVItem1
    Item appleTVItem2
    Item appleTVItem3
    Item appleTVItem4
    Item appleTVItem5
    Item appleTVItem6
    Item macBookItem

    def setup() {
        rule = new ThreeForTwoAppleTVsPricingRule()

        appleTV = Mock(Product)
        macBook = Mock(Product)

        appleTV.isAppleTV() >> true
        macBook.isAppleTV() >> false

        appleTVItem1 = Mock(Item)
        appleTVItem2 = Mock(Item)
        appleTVItem3 = Mock(Item)
        appleTVItem4 = Mock(Item)
        appleTVItem5 = Mock(Item)
        appleTVItem6 = Mock(Item)
        macBookItem = Mock(Item)

        appleTVItem1.getProduct() >> appleTV
        appleTVItem2.getProduct() >> appleTV
        appleTVItem3.getProduct() >> appleTV
        appleTVItem4.getProduct() >> appleTV
        appleTVItem5.getProduct() >> appleTV
        appleTVItem6.getProduct() >> appleTV
        macBookItem.getProduct() >> macBook

    }

    def "isValid should return true"() {
        expect:
        rule.isValid()
    }

    def "applyRule should not change the price of the appleTV item if there are only 2 appleTVs in the list"() {
        given:
        List items = [appleTVItem1, appleTVItem2]

        when:
        rule.applyRule(items)

        then:
        0 * appleTVItem1.bundleForFree()
        0 * appleTVItem2.bundleForFree()
    }

    def "applyRule should change the price of only one appleTV item if there are 3 appleTVs in the list"() {
        given:
        List items = [appleTVItem1, appleTVItem2, appleTVItem3, macBookItem]

        when:
        rule.applyRule(items)

        then:
        1 * appleTVItem1.bundleForFree()
        0 * appleTVItem2.bundleForFree()
        0 * appleTVItem3.bundleForFree()
        0 * macBookItem.bundleForFree()
    }

    def "applyRule should change the price of only two appleTV item if there are 6 ippleTVs in the list"() {
        given:
        List items = [appleTVItem1, appleTVItem2,
                      appleTVItem3, appleTVItem4,
                      appleTVItem5, appleTVItem6]

        when:
        rule.applyRule(items)

        then:
        1 * appleTVItem1.bundleForFree()
        1 * appleTVItem2.bundleForFree()
        0 * appleTVItem3.bundleForFree()
        0 * appleTVItem4.bundleForFree()
        0 * appleTVItem5.bundleForFree()
        0 * appleTVItem6.bundleForFree()
        0 * macBookItem.bundleForFree()
    }
}
