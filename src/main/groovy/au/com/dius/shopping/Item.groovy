package au.com.dius.shopping

class Item {
    private BigDecimal price
    private Product product

    def Item(BigDecimal price, Product product) {
        this.price = price
        this.product = product
    }
}
