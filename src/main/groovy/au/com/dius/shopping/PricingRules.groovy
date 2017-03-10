package au.com.dius.shopping

import au.com.dius.shopping.pricingRules.AbstractPricingRule
import au.com.dius.shopping.pricingRules.FreeVGAAdapterWithMacBookProPricingRule
import au.com.dius.shopping.pricingRules.SuperIPadBulkDiscountPricingRule
import au.com.dius.shopping.pricingRules.ThreeForTwoAppleTVsPricingRule

class PricingRules {
    List<AbstractPricingRule> pricingRules = []

    PricingRules() {
        pricingRules << new FreeVGAAdapterWithMacBookProPricingRule()
        pricingRules << new SuperIPadBulkDiscountPricingRule()
        pricingRules << new ThreeForTwoAppleTVsPricingRule()
    }

    void applyRules(List<Item> items) {
        pricingRules.each { it.applyRule(items) }
    }
}
