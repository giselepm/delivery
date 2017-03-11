package au.com.dius.shopping

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class ProductSpec extends Specification {
    def "isAppleTV should return #result when the sku is #sku"() {
        given:
        Product product = new Product(sku, "aName", 10.00)

        expect:
        product.isAppleTV() == result

        where:
        sku   | result
        "atv" | true
        "ipd" | false
        "abc" | false
    }

    def "isSuperIpad should return #result when the sku is #sku"() {
        given:
        Product product = new Product(sku, "aName", 10.00)

        expect:
        product.isSuperIpad() == result

        where:
        sku   | result
        "atv" | false
        "ipd" | true
        "abc" | false
    }

    def "isMacBookPro should return #result when the sku is #sku"() {
        given:
        Product product = new Product(sku, "aName", 10.00)

        expect:
        product.isMacBookPro() == result

        where:
        sku   | result
        "mbp" | true
        "ipd" | false
        "abc" | false
    }

    def "isVGAAdapter should return #result when the sku is #sku"() {
        given:
        Product product = new Product(sku, "aName", 10.00)

        expect:
        product.isVGAAdapter() == result

        where:
        sku   | result
        "vga" | true
        "ipd" | false
        "abc" | false
    }
}
