package by.bstu.vs.stpms.lr13.data.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.text.format.DateUtils
import android.text.format.DateUtils.MINUTE_IN_MILLIS
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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

fun Date.articleFormat(): String = DateUtils
    .getRelativeTimeSpanString(this.time, Date().time, MINUTE_IN_MILLIS)
    .toString()


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