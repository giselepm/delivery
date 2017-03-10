package au.com.dius.shopping.pricingRules

import au.com.dius.shopping.Item

class FreeVGAAdapterWithMacBookProPricingRule extends AbstractPricingRule  {

    boolean isValid() {
        true
    }

    void applyRule(List<Item> items) {
        List<Item> macBookItems = items.findAll { it.product.sku == "mbp" }
        List<Item> vgaAdapterItems = items.findAll { it.product.sku == "vga" }

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
