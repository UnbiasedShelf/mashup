package by.bstu.vs.stpms.lr13.ui

import android.Manifest
import android.annotation.SuppressLint
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import by.bstu.vs.stpms.lr13.data.model.LocationCity
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import java.util.*


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
    @ExperimentalPermissionsApi
    @Composable
    fun MainScreen(mainViewModel: MainViewModel = viewModel()) {

        val city by mainViewModel.city.observeAsState(initial = "")
        val permissionState = rememberPermissionState(permission = Manifest.permission.ACCESS_COARSE_LOCATION)
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
                val locationProviderClient = LocationServices.getFusedLocationProviderClient(this@MainActivity)
                locationProviderClient.lastLocation.addOnSuccessListener {
                    val geocoder = Geocoder(this@MainActivity)
                    val addresses = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                    val city = addresses.first().locality
                    val country = addresses.first().countryName
                    val locationCity = LocationCity(it.latitude, it.longitude, city, country)
                    Log.i("MainActivity", "onLocationCityReceived: Received $locationCity")
                    mainViewModel.onCityChanged(locationCity.city)
                }
            }

            WeatherWidget(
                city = city,
                onCityChange = mainViewModel::onCityChanged,
                onSubmit = mainViewModel::getWeather
            )
        }
    }

    @Composable
    fun WeatherWidget(
        city: String,
        onCityChange: (String) -> Unit,
        onSubmit: () -> Unit
    ) {
        val margin = 8.dp
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                TextField(
                    value = city,
                    onValueChange = onCityChange,
                    placeholder = {
                        Text(text = "City")
                    },
                    modifier = Modifier.padding(margin),
                )
                Button(
                    onClick = onSubmit,
                    modifier = Modifier.padding(margin),
                ) {
                    Text(text = "SUBMIT")
                }
            }

        }
    }

    @Preview
    @Composable
    fun DefaultPreview() {
        WeatherWidget(city = "", onCityChange = { }, onSubmit = { })
    }
}

