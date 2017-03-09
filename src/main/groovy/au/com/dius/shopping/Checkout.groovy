package au.com.dius.shopping

class Checkout {
    private List<Product> items = []
    private PricingRules pricingRules

    Checkout(PricingRules pricingRules) {
        this.pricingRules = pricingRules
    }

    String total() {
        "\$${pricingRules.calculateTotalPrice(items)}"
    }

    void scan(Product product) {
        items << product
    }
}
