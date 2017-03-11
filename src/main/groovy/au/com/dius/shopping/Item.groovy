package au.com.dius.shopping

class Item {
    private BigDecimal price
    private Product product

    Item(Product product) {
        price = product.price
        this.product = product
    }

    void bundleForFree() {
        price = 0.00
    }
}
