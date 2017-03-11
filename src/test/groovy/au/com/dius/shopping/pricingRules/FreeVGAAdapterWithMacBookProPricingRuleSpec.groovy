package au.com.dius.shopping.pricingRules

import au.com.dius.shopping.Item
import au.com.dius.shopping.Product
import spock.lang.Specification

class FreeVGAAdapterWithMacBookProPricingRuleSpec extends Specification {
    FreeVGAAdapterWithMacBookProPricingRule rule
    Product vga
    Product macBook

    def setup() {
        rule = new FreeVGAAdapterWithMacBookProPricingRule()
        vga = new Product("vga", "VGA Adapter", 10.00)
        macBook = new Product("mbp", "MacBook Pro", 30.00)
    }

    def "isValid should return true"() {
        expect:
        rule.isValid()
    }

    def "applyRule should not change the price of the vgaAdapter item if there is no macBook in the list"() {
        given:
        List items = [new Item(vga), new Item(vga)]

        when:
        rule.applyRule(items)

        then:
        items.price == [10.00, 10.00]
    }

    def "applyRule should change the price of only one vgaAdapter item if there is one macBook in the list"() {
        given:
        List items = [new Item(vga), new Item(vga), new Item(macBook)]

        when:
        rule.applyRule(items)

        then:
        items.findAll { it.product.sku == "vga" }.price.containsAll([0.00, 10.00])
        items.find { it.product.sku == "mbp" }.price == 30.00
    }

    def "applyRule should change the price of only two vgaAdapter item if there is two macBook in the list"() {
        given:
        List items = [new Item(vga), new Item(vga),
                      new Item(macBook), new Item(macBook),
                      new Item(vga), new Item(vga)]

        when:
        rule.applyRule(items)

        then:
        items.findAll { it.product.sku == "vga" && it.price == 0.00 }.size() == 2
        items.findAll { it.product.sku == "vga" && it.price == 10.00 }.size() == 2
        items.findAll { it.product.sku == "mbp" && it.price == 30.00 }.size() == 2
    }
}
