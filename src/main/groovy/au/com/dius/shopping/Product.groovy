package au.com.dius.shopping

class Product {
    private String sku
    private String name
    private BigDecimal price

    def Product(String sku, String name, BigDecimal price) {
        this.sku = sku
        this.name = name
        this.price = price
    }
}
