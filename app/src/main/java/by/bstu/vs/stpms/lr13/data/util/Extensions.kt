package by.bstu.vs.stpms.lr13.data.util

import java.util.*
import kotlin.math.round

val Locale.units: String
get() {
    return when(country) {
        "US", "LR", "MM" -> "imperial"
        else -> "metric"
    }
}

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}

