package au.com.dius.shopping

import spock.lang.Specification

class ItemSpec extends Specification {
    Product product

    def setup() {
        product = Mock(Product)
        product.getPrice() >> 20.00
    }

    def "when an Item is created it has the same value of the product"() {
        given:
        Item item = new Item(product)

        expect:
        item.price == 20.00
    }

    def "bundleForFree should change the price to 0.00"() {
        given:
        Item item = new Item(product)

        when:
        item.bundleForFree()

        then:
        item.price == 0.00
    }
}
