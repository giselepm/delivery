package au.com.dius.shopping

class Item {
    private BigDecimal price
    private Product product

    Item(BigDecimal price, Product product) {
        this.price = price
        this.product = product
    }

    void bundleForFree() {
        price = 0.0
    }
}
