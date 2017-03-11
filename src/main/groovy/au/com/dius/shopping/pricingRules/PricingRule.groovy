package au.com.dius.shopping.pricingRules

import au.com.dius.shopping.Item

interface PricingRule {
    boolean isValid()
    void applyRule (List<Item> items)

}
