package au.com.dius.shopping

class Checkout {
    private BigDecimal total = 0.00
    private List<Product> items = []

    Checkout(PricingRules pricingRules) {

    }

    String total() {
        int appleTVCounter = 0
        items.each { Product item ->
            if (item.sku == "atv") {
                appleTVCounter++
            }
            total += item.price
        }

        if (appleTVCounter >= 3) {
            total -= 109.50
        }

        "\$${total}"
    }

    void scan(Product product) {
        items << product
    }
}
