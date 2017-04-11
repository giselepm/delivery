package de.com.allpago.delivery

class Package {
    BigDecimal weight
    BigDecimal width
    BigDecimal length
    BigDecimal height

    Package(BigDecimal weight, BigDecimal width, BigDecimal length, BigDecimal height) {
        this.weight = weight.abs()
        this.width = width.abs()
        this.length = length.abs()
        this.height = height.abs()
    }
}
