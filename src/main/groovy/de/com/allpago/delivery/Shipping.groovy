package de.com.allpago.delivery

import java.math.RoundingMode

class Shipping {

    private static final BigDecimal ROUND_FACTOR = 0.5

     private static BigDecimal calculateVolumetricWeight(Package aPackage) {
         BigDecimal volumetricWeight =  aPackage.width * aPackage.length * aPackage.height / 5000

        return (volumetricWeight / ROUND_FACTOR).setScale( 0, RoundingMode.CEILING ) * ROUND_FACTOR

    }

     private static BigDecimal calculateNormalizedWeight(Package aPackage) {
        return [aPackage.weight, calculateVolumetricWeight(aPackage)].max()

    }

    static BigDecimal calculateShippingCost(Integer hard, Package aPackage) {
        if (hard == null || !aPackage) {
            return null
        }
        if (hard == Integer.MAX_VALUE) {
            return Double.MAX_VALUE as BigDecimal
        }

        BigDecimal shippingCost = Math.sqrt(hard.abs()) * calculateNormalizedWeight(aPackage)

        return shippingCost.setScale(2, BigDecimal.ROUND_HALF_UP)

    }
}
