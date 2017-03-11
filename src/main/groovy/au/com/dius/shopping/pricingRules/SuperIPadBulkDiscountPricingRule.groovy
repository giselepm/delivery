package au.com.dius.shopping.pricingRules

import au.com.dius.shopping.Item

class SuperIPadBulkDiscountPricingRule implements PricingRule {

    private static final int MIN_NUMBER_OF_IPADS_FOR_BULK_DISCOUNT = 4
    private static final BigDecimal DISCOUNT_PRICE = 499.99

    boolean isValid() {
        true
    }

    void applyRule(List<Item> items) {
        List<Item> iPadItems = items.findAll { it.product.isSuperIpad() }

        if (iPadItems && iPadItems.size() > MIN_NUMBER_OF_IPADS_FOR_BULK_DISCOUNT) {
            iPadItems.each { Item item ->
                item.price = DISCOUNT_PRICE
            }
        }
    }
}
