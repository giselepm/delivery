package au.com.dius.shopping

class Checkout {
    private BigDecimal total = 0.00
    private List<Product> items = []

    Checkout(PricingRules pricingRules) {

    }

    String total() {
        List<Product> appleTVItems = []
        List<Product> iPadItems = []
        List<Product> macBookItems = []
        List<Product> vgaAdapterItems = []

        items.each { Product item ->
            if (item.sku == "atv") {
                appleTVItems << item
            } else if (item.sku == "ipd") {
                iPadItems << item
            } else if (item.sku == "mbp") {
                macBookItems << item
            } else if (item.sku == "vga") {
                vgaAdapterItems << item
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

        if (macBookItems && vgaAdapterItems) {
            total -= vgaAdapterItems.size() * vgaAdapterItems.price
        }

        "\$${total}"
    }

    void scan(Product product) {
        items << product
    }
}
