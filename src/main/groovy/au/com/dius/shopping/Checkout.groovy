package au.com.dius.shopping

class Checkout {
    private BigDecimal total = 0.00
    private List<Product> items = []

    Checkout(PricingRules pricingRules) {

    }

    String total() {
        List<Product> appleTVItems = []
        List<Product> iPadItems = []
        List<Product> otherItems = []
        items.each { Product item ->
            if (item.sku == "atv") {
                appleTVItems << item
            } else if (item.sku == "ipd") {
                iPadItems << item
            } else {
                otherItems << item
            }
            total += item.price
        }

        if (appleTVItems && appleTVItems.size() >= 3) {
            total -= ((int)(appleTVItems.size()) / 3) * 109.50
        }
        if (iPadItems && iPadItems.size() > 4) {
            total -= iPadItems.first().price * iPadItems.size()
            total += iPadItems.size() * 499.99
        }

        "\$${total}"
    }

    void scan(Product product) {
        items << product
    }
}
