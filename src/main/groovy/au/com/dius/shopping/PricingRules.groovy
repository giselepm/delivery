package au.com.dius.shopping

class PricingRules {
    BigDecimal calculateTotalPrice(List<Item> items) {
        BigDecimal total = 0.00
        List<Product> appleTVItems = []
        List<Product> iPadItems = []
        List<Product> macBookItems = []
        List<Product> vgaAdapterItems = []

        items.each { Item item ->
            if (item.product.sku == "atv") {
                appleTVItems << item
            } else if (item.product.sku == "ipd") {
                iPadItems << item
            } else if (item.product.sku == "mbp") {
                macBookItems << item
            } else if (item.product.sku == "vga") {
                vgaAdapterItems << item
            }
            total += item.price
        }

        if (appleTVItems && appleTVItems.size() >= 3) {
            total -= ((int) (appleTVItems.size()) / 3) * 109.50
        }

        if (iPadItems && iPadItems.size() > 4) {
            total -= iPadItems.first().price * iPadItems.size()
            total += iPadItems.size() * 499.99
        }

        if (macBookItems && vgaAdapterItems) {
            total -= [macBookItems.size(), vgaAdapterItems.size()].min() * vgaAdapterItems.first().price
        }

        return total
    }
}
