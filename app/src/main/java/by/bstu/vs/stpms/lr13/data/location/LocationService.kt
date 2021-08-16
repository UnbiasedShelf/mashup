package by.bstu.vs.stpms.lr13.data.location

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Singleton that provides information about [Location]
 */
object LocationService {
    val TAG = "LocationService"

    /**
     * Returns current device [Location] by using [Context]. Requires ACCESS_FINE_LOCATION.
     * @param context The instance of [Context] that needed to create [FusedLocationProviderClient]
     * @return A [Location] of the current device or null if [Location] doesn't received successfully
     */
    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    suspend fun getLocation(context: Context): Location? {
        return suspendCoroutine { continuation ->
            val locationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
            locationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, null).addOnSuccessListener {
                Log.i(TAG, "getLocation: received $it")
                continuation.resume(it)
            }
        }
    }
}