package com.challenge.meli.utils

import java.math.RoundingMode
import java.text.DecimalFormat

class NumberHelper {

    fun parseAmountToCOP(amount: Double): String? {
        val dec = DecimalFormat("#,###")
        dec.roundingMode = RoundingMode.CEILING
        return dec.format(amount)
    }

}
