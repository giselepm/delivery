package au.com.dius.shopping

class Checkout {
    private String total = "\$0.00"

    Checkout(PricingRules pricingRules) {

    }

    String total(){
        total
    }

    void scan(Product product) {
        total = "\$${product.price}"
    }
}
