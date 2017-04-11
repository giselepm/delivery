package de.com.allpago.delivery

import java.math.RoundingMode

class Shipping {

    private static final BigDecimal ROUND_FACTOR = 0.5

     private static BigDecimal calculateVolumetricWeight(BigDecimal width, BigDecimal length, BigDecimal height) {
         BigDecimal volumetricWeight =  width * length * height / 5000

        return (volumetricWeight / ROUND_FACTOR).setScale( 0, RoundingMode.CEILING ) * ROUND_FACTOR

    }

     private static BigDecimal calculateNormalizedWeight(BigDecimal weight, BigDecimal width, BigDecimal length, BigDecimal height) {
        return [weight, calculateVolumetricWeight(width, length, height)].max()

    }

    static BigDecimal calculateShippingCost(int hard, BigDecimal weight, BigDecimal width, BigDecimal length, BigDecimal height) {
        if (hard < 0 || weight < 0 || width < 0 || length < 0 || height < 0) {
            return null
        }

        BigDecimal shippingCost = Math.sqrt(hard) * calculateNormalizedWeight(weight, width, length, height)

        return shippingCost.setScale(2, BigDecimal.ROUND_DOWN)

    }
}
