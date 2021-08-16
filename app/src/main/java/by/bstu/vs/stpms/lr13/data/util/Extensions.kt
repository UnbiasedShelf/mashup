package by.bstu.vs.stpms.lr13.data.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.text.format.DateUtils
import android.text.format.DateUtils.MINUTE_IN_MILLIS
import android.util.Log
import by.bstu.vs.stpms.lr13.R
import by.bstu.vs.stpms.lr13.data.model.MeasureUnits
import by.bstu.vs.stpms.lr13.data.model.Weather
import java.util.*
import kotlin.math.round

/**
 * @return [MeasureUnits] by Locale country
 */
val Locale.units: MeasureUnits
get() {
    return when(country) {
        "US", "LR", "MM" -> MeasureUnits.IMPERIAL
        else -> MeasureUnits.METRIC
    }
}

/**
 * Checks if [String] as image url is valid
 * @return [Boolean]
 */
fun String.isImageUrlValid(): Boolean {
    val imageUrlPattern = "(https?:\\/\\/.*\\.(?:png|jpg|jpeg)(\\?|&.*)?)"
    return this.matches(imageUrlPattern.toRegex())
}

/**
 * Rounds [Double] to given digits after floating comma
 * @param digits int amount of digits after floating comma
 */
fun Double.round(digits: Int): Double {
    var multiplier = 1.0
    repeat(digits) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}

/**
 * Formats [Date] to relative time span [String]
 */
fun Date.articleFormat(): String = DateUtils
    .getRelativeTimeSpanString(this.time, Date().time, MINUTE_IN_MILLIS)
    .toString()

/**
 * @param rawDirection string equivalent of windDirectionDegrees property of [Weather].
 * Possible values: W, SW, S, SE, E, NE, N, NW, --
 * @return localized wind direction string
 */
fun Context.getLocalizedWindDirection(rawDirection: String) = rawDirection
    .map {
        when(it) {
            'N' -> getString(R.string.direction_north)
            'W' -> getString(R.string.direction_west)
            'S' -> getString(R.string.direction_south)
            'E' -> getString(R.string.direction_east)
            else -> "-"
        }
    }
    .joinToString(separator = "")

/**
 * Return localized string of measure units from resources
 * @param units [MeasureUnits] of device
 */
fun Context.getLocalizedSpeedUnits(units: MeasureUnits) = when(units) {
    MeasureUnits.IMPERIAL -> getString(R.string.miles_per_hour)
    MeasureUnits.METRIC -> getString(R.string.meters_per_second)
}

/**
 * Checks is device connected to network either by Wi-Fi or by mobile network
 * @param context The [Context] for getting [ConnectivityManager]
 * @return [Boolean] is device connected
 */
fun isConnectedToNetwork(context: Context): Boolean {
    val isConnected: Boolean
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    isConnected = if (capabilities != null) {
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    } else {
        false
    }
    Log.i("isConnectedToNetwork", isConnected.toString())
    return isConnected
}