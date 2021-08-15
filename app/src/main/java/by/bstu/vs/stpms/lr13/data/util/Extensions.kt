package by.bstu.vs.stpms.lr13.data.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.text.format.DateUtils
import android.text.format.DateUtils.MINUTE_IN_MILLIS
import android.util.Log
import by.bstu.vs.stpms.lr13.R
import by.bstu.vs.stpms.lr13.data.model.MeasureUnits
import java.util.*
import kotlin.math.round

val Locale.units: MeasureUnits
get() {
    return when(country) {
        "US", "LR", "MM" -> MeasureUnits.IMPERIAL
        else -> MeasureUnits.METRIC
    }
}

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}

fun Date.articleFormat(): String = DateUtils
    .getRelativeTimeSpanString(this.time, Date().time, MINUTE_IN_MILLIS)
    .toString()

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

fun Context.getLocalizedSpeedUnits(units: MeasureUnits) = when(units) {
    MeasureUnits.IMPERIAL -> getString(R.string.miles_per_hour)
    MeasureUnits.METRIC -> getString(R.string.meters_per_second)
}

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