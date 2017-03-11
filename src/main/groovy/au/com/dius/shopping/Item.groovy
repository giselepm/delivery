package au.com.dius.shopping

class Item {
    BigDecimal price
    Product product

    Item(Product product) {
        this.price = product.price
        this.product = product
    }

    void bundleForFree() {
        price = 0.00
    }
}
