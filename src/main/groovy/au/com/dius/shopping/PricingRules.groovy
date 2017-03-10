package au.com.dius.shopping

class PricingRules {
    void applyRules(List<Item> items) {
        List<Item> appleTVItems = items.findAll { it.product.sku == "atv" }
        List<Item> iPadItems = items.findAll { it.product.sku == "ipd" }
        List<Item> macBookItems = items.findAll { it.product.sku == "mbp" }
        List<Item> vgaAdapterItems = items.findAll { it.product.sku == "vga" }

        if (appleTVItems && appleTVItems.size() >= 3) {
            int freeAppleTVsAmount = (int)(appleTVItems.size()) / 3
            appleTVItems.eachWithIndex{ Item item, int i ->
                if (i < freeAppleTVsAmount) {
                    item.price = 0.00
                }
            }
        }

        if (iPadItems && iPadItems.size() > 4) {
            iPadItems.each{ Item item ->
                item.price = 499.99
            }
        }

        if (macBookItems && vgaAdapterItems) {
            int freeVgaAdaptersAmount = [macBookItems.size(), vgaAdapterItems.size()].min()
            vgaAdapterItems.eachWithIndex{ Item item, int i ->
                if (i < freeVgaAdaptersAmount) {
                    item.price = 0.00
                }
            }
        }
    }
}
