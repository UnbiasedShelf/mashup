package by.bstu.vs.stpms.lr13.data.util

import java.util.*

val Locale.units: String
get() {
    return when(country) {
        "US", "LR", "MM" -> "imperial"
        else -> "metric"
    }
}