package by.bstu.vs.stpms.lr13.data.location

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object LocationService {
    val TAG = "LocationService"

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    suspend fun getLocation(context: Context): Location? {
        return suspendCoroutine { continuation ->
            val locationProviderClient = LocationServices.getFusedLocationProviderClient(context)
            locationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, null).addOnSuccessListener {
                Log.i(TAG, "getLocation: received $it")
                continuation.resume(it)
            }
        }
    }
}