package au.com.dius.shopping.pricingRules

import au.com.dius.shopping.Item

class ThreeForTwoAppleTVsPricingRule implements PricingRule {

    private static final int TOTAL_APPLE_TVS_FOR_DISCOUNT = 3
    private static final BigDecimal DISCOUNT_PRICE = 0.00

    boolean isValid() {
        true
    }

    void applyRule(List<Item> items) {
        List<Item> appleTVItems = items.findAll { it.product.isAppleTV() }

        if (appleTVItems && appleTVItems.size() >= TOTAL_APPLE_TVS_FOR_DISCOUNT) {
            int freeAppleTVsAmount = (int)(appleTVItems.size()) / TOTAL_APPLE_TVS_FOR_DISCOUNT
            appleTVItems.eachWithIndex{ Item item, int i ->
                if (i < freeAppleTVsAmount) {
                    item.price = DISCOUNT_PRICE
                }
            }
        }
    }
}
