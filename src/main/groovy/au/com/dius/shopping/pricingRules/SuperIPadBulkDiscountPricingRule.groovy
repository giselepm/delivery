package au.com.dius.shopping.pricingRules

import au.com.dius.shopping.Item

class SuperIPadBulkDiscountPricingRule extends AbstractPricingRule  {

    boolean isValid() {
        true
    }

    void applyRule(List<Item> items) {
        List<Item> iPadItems = items.findAll { it.product.sku == "ipd" }

        if (iPadItems && iPadItems.size() > 4) {
            iPadItems.each{ Item item ->
                item.price = 499.99
            }
        }
    }
}
