package by.bstu.vs.stpms.lr13.data.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.annotation.RequiresPermission
import by.bstu.vs.stpms.lr13.data.model.LocationCity
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object LocationService {
    val TAG = "LocationService"

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    suspend fun getLocationCity(context: Context): LocationCity? {
        return suspendCoroutine { continuation ->
            val locationProviderClient = LocationServices.getFusedLocationProviderClient(context)
            locationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, null).addOnSuccessListener {
                Log.i(TAG, "getLocationCity: received $it")
                val locationCity: LocationCity? = mapLocationToLocationCity(it, context)
                continuation.resume(locationCity)
            }
        }
    }

    private fun mapLocationToLocationCity(location: Location?, context: Context): LocationCity? {
        return if (location != null) {
            val geocoder = Geocoder(context)
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            val city = addresses.first().locality
            val country = addresses.first().countryName
            val locationCity = LocationCity(location.latitude, location.longitude, city, country)
            Log.i(TAG, "mapLocationToLocationCity: return $locationCity")

            locationCity
        } else {
            Log.w(TAG, "mapLocationToLocationCity: return null")

            null
        }
    }
}