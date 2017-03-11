package au.com.dius.shopping

class Checkout {
    private List<Item> items = []
    private PricingRules pricingRules

    Checkout(PricingRules pricingRules) {
        this.pricingRules = pricingRules
    }

    String total() {
        pricingRules.applyRules(items)

        "\$${items.price.sum() ?: 0.00}"
    }

    void scan(Product product) {
        items << new Item(product)
    }
}
