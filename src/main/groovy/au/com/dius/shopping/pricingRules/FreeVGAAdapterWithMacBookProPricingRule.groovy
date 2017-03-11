package au.com.dius.shopping.pricingRules

import au.com.dius.shopping.Item

class FreeVGAAdapterWithMacBookProPricingRule implements PricingRule {

    boolean isValid() {
        true
    }

    void applyRule(List<Item> items) {
        List<Item> macBookItems = items.findAll { it.product.isMacBookPro() }
        List<Item> vgaAdapterItems = items.findAll { it.product.isVGAAdapter() }

        if (macBookItems && vgaAdapterItems) {
            int totalFreeVgaAdapters = [macBookItems.size(), vgaAdapterItems.size()].min()
            vgaAdapterItems.eachWithIndex { Item item, int i ->
                if (i < totalFreeVgaAdapters) {
                    item.bundleForFree()
                }
            }
        }
    }
}
