package au.com.dius.shopping

import spock.lang.Specification

class CheckoutSpec extends Specification {
    Checkout checkout
    Product productAbc
    Product productFgh
    Product productXyz

    def setup() {
        checkout = new Checkout(new PricingRules())
        productAbc = new Product("abc", "ABC", 10.00)
        productFgh = new Product("fgh", "FGH", 35.99)
        productXyz = new Product("abc", "XYZ", 300.50)
    }

    def "scan should add an item with the same price of the product and link it in the items list"() {
        when:
        checkout.scan(productAbc)

        then:
        checkout.items.size() == 1
        checkout.items.first().price == productAbc.price
        checkout.items.first().product == productAbc
    }

    def "when no item was scanned, total should return \$0.00"() {
        when:
        String total = checkout.total()

        then:
        total == "\$0.00"
    }

    def "when only one item was scanned, total should return its price formatted"() {
        given:
        checkout.scan(productAbc)

        when:
        String total = checkout.total()

        then:
        total == "\$${productAbc.price}"
    }

    def "when two items were scanned, total should return their prices' sum formatted"() {
        given:
        checkout.scan(productAbc)
        checkout.scan(productFgh)

        when:
        String total = checkout.total()

        then:
        total == "\$${productAbc.price + productFgh.price}"
    }

    def "when three items were scanned, total should return their prices' sum formatted"() {
        given:
        checkout.scan(productAbc)
        checkout.scan(productFgh)
        checkout.scan(productXyz)

        when:
        String total = checkout.total()

        then:
        total == "\$${productAbc.price + productFgh.price + productXyz.price}"
    }
}
