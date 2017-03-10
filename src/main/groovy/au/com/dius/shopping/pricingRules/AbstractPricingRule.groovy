package au.com.dius.shopping.pricingRules

import au.com.dius.shopping.Item

abstract class AbstractPricingRule {
    abstract void applyRule (List<Item> items)

}
