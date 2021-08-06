package by.bstu.vs.stpms.lr13.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import by.bstu.vs.stpms.lr13.data.model.LocationCity
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.android.gms.location.LocationServices
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class MainActivity : ComponentActivity() {

    private val TAG = "MainActivity"

    @ExperimentalPermissionsApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainScreen()
        }
    }

    //Permission checked by PermissionRequired
    @SuppressLint("MissingPermission")
    private suspend fun getLocationCity(): LocationCity? {
        return suspendCoroutine { continuation ->
            val locationProviderClient = LocationServices.getFusedLocationProviderClient(this@MainActivity)
            locationProviderClient.lastLocation.addOnSuccessListener {
                if (it != null) {
                    val geocoder = Geocoder(this@MainActivity)
                    val addresses = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                    val city = addresses.first().locality
                    val country = addresses.first().countryName
                    val locationCity = LocationCity(it.latitude, it.longitude, city, country)
                    Log.i("MainActivity", "getLocationCity: Received $locationCity")
                    continuation.resume(locationCity)
                } else {
                    Log.w("MainActivity", "getLocationCity: Received null")
                    continuation.resume(null)
                }
            }
        }

    }

    @ExperimentalPermissionsApi
    @Composable
    fun MainScreen(mainViewModel: MainViewModel = viewModel()) {

        val city by mainViewModel.city.observeAsState(initial = LocationCity())
        val permissionState = rememberPermissionState(permission = Manifest.permission.ACCESS_COARSE_LOCATION)
        val isRefreshing by mainViewModel.isRefreshing.collectAsState()
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
            onRefresh = {
                lifecycleScope.launchWhenStarted {
                    mainViewModel.onCityChanged(getLocationCity())
                }
            }) {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                PermissionRequired(
                    permissionState = permissionState,
                    permissionNotGrantedContent = {
                        LaunchedEffect(true) {
                            permissionState.launchPermissionRequest()
                        }
                    },
                    permissionNotAvailableContent = {
                        Column {
                            Text(
                                "Location permission denied. See this FAQ with information about why we " +
                                        "need this permission. Please, grant us access on the Settings screen."
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                        }
                    }
                ) {

                    LaunchedEffect(true) {
                        mainViewModel.onCityChanged(getLocationCity())
                    }

                    WeatherWidget(
                        location = city,
                        onCityChange = mainViewModel::onCityChanged,
                        onSubmit = mainViewModel::getWeather
                    )
                }
            }

        }


    }

    @Composable
    fun WeatherWidget(
        location: LocationCity?,
        onCityChange: (LocationCity) -> Unit,
        onSubmit: (String, String) -> Unit
    ) {
        val margin = 8.dp
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                location?.city?.let {
                    TextField(
                        value = it,
                        onValueChange = { },
                        placeholder = {
                            Text(text = "City")
                        },
                        modifier = Modifier.padding(margin),
                    )
                }
                Button(
                    onClick = {
                        onSubmit("", "")
                    },
                    modifier = Modifier.padding(margin),
                ) {
                    Text(text = "SUBMIT")
                }
            }

        }
    }
}

