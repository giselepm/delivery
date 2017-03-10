package au.com.dius.shopping

class Product {
    private String sku
    private String name
    private BigDecimal price

    Product(String sku, String name, BigDecimal price) {
        this.sku = sku
        this.name = name
        this.price = price
    }

    boolean isAppleTV() {
        sku == "atv"
    }

    boolean isSuperIpad() {
        sku == "ipd"
    }

    boolean isMacBookPro() {
        sku == "mbp"
    }

    boolean isVGAAdapter() {
        sku == "vga"
    }
}
