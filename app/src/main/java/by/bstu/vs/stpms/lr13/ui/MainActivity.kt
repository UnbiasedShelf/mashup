package by.bstu.vs.stpms.lr13.ui

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import by.bstu.vs.stpms.lr13.R
import by.bstu.vs.stpms.lr13.data.location.LocationService
import by.bstu.vs.stpms.lr13.data.model.MeasureUnits
import by.bstu.vs.stpms.lr13.data.model.Weather
import by.bstu.vs.stpms.lr13.data.util.units
import by.bstu.vs.stpms.lr13.ui.theme.MashupTheme
import coil.compose.rememberImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.util.*
import kotlin.math.roundToInt


class MainActivity : ComponentActivity() {

    private val TAG = "MainActivity"

    @ExperimentalPermissionsApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainScreen()
        }
    }

    @ExperimentalPermissionsApi
    @Composable
    @SuppressLint("MissingPermission") // Permission checked by PermissionRequired Composable
    fun MainScreen(mainViewModel: MainViewModel = viewModel()) {
        val permissionState = rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)
        val isRefreshing by mainViewModel.isRefreshing.collectAsState()
        MashupTheme {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = stringResource(id = R.string.app_name))
                        }
                    )
                },
            ) {
                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
                    onRefresh = {
                        lifecycleScope.launchWhenStarted {
                            updateUI(mainViewModel)
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
                                updateUI(mainViewModel)
                            }

                            WeatherWidget(
                                weather = mainViewModel.weather
                            )
                        }
                    }

                }
            }
        }
    }

    @Composable
    fun WeatherWidget(
        weather: Weather?
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            elevation = 4.dp,
            backgroundColor = MaterialTheme.colors.secondary
        ) {
            Box(modifier = Modifier
                .height(200.dp)
                .padding(16.dp)
            ) {
                Column {
                    if (weather != null) {
                        //TODO remove string to resources
                        Text(
                            text = weather.city ?: "Undefined",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                        Divider(thickness = 1.dp)
                        Text(
                            text = weather.description ?: "Undefined",
                            fontSize = 18.sp,
                            fontStyle = FontStyle.Italic
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .offset(x = (-20).dp)
                        ) {
                            Image(
                                painter = rememberImagePainter(weather.icon),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(150.dp)
                            )
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            fontSize = 60.sp
                                        )
                                    ) {
                                        append(weather.temperature)
                                    }
                                    withStyle(style = SpanStyle(
                                        baselineShift = BaselineShift(+2.2f),
                                        fontSize = 20.sp
                                    )) {
                                        append(weather.temperatureUnits)
                                    }
                                },
                                modifier = Modifier
                                    .offset(x = (-20).dp)
                            )
                        }
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    fun Preview() {
        MashupTheme {
            WeatherWidget(weather = Weather(
                city = "Los Angeles",
                description = "Clear",
                temperature = "92",
                temperatureUnits = "°F",
                humidity = "40%",
                windSpeed = "2 mph",
                windDirection = "N",
                windDirectionDegrees = 0,
            ))
        }
    }

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    private suspend fun updateUI(mainViewModel: MainViewModel) {
        val location = LocationService.getLocation(this@MainActivity)
        val locale = Locale.getDefault()
        Log.d(TAG, "MainScreen: $locale")
        location?.let {
            mainViewModel.getWeather(
                location = it,
                language = locale.language,
                units = MeasureUnits.valueOf(locale.units.uppercase(locale))
            )
        }

    }
}

