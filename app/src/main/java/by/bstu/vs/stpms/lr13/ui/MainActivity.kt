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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
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
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.fade
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.util.*


//TODO test on small screens
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
        Log.i(TAG, "weather widget called: $weather")
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .placeholder(
                    visible = weather == null,
                    highlight = PlaceholderHighlight.shimmer()
                ),
            elevation = 4.dp,
            backgroundColor = MaterialTheme.colors.secondary
        ) {
            Box(modifier = Modifier
                .height(200.dp)
                .padding(16.dp)
            ) {
                Column {
                    PlaceholderText(
                        text = weather?.city,
                        placeholderSymbolLength = 20,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                    Divider(
                        thickness = 1.dp,
                        color = MaterialTheme.colors.onPrimary
                    )
                    PlaceholderText(
                        text = weather?.description,
                        placeholderSymbolLength = 30,
                        fontSize = 20.sp,
                        fontStyle = FontStyle.Italic
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .offset(x = (-20).dp)
                    ) {
                        Image(
                            painter = rememberImagePainter(weather?.icon),
                            contentDescription = null,
                            modifier = Modifier
                                .size(150.dp)
                        )
                        PlaceholderText(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 60.sp
                                    )
                                ) {
                                    //Spaces used to make placeholder visible
                                    append(weather?.temperature ?: " ".repeat(6))
                                }
                                withStyle(style = SpanStyle(
                                    baselineShift = BaselineShift(+2f),
                                    fontSize = 20.sp
                                )) {
                                    append(weather?.temperatureUnits ?: "")
                                }
                            },
                            modifier = Modifier
                                .offset(x = (-20).dp)
                        )
                        Column(
                            Modifier.fillMaxWidth()
                        ) {

                            Row {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_humidity),
                                    contentDescription = "humidity",
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                PlaceholderText(
                                    text = weather?.humidity,
                                    placeholderSymbolLength = 7,
                                    fontSize = 20.sp,
                                )
                            }

                            Row {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_wind),
                                    contentDescription = "wind",
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                PlaceholderText(
                                    text = weather?.windSpeed,
                                    placeholderSymbolLength = 7,
                                    fontSize = 20.sp
                                )
                            }

                            Row {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_baseline_arrow_right_alt_24),
                                    contentDescription = "direction",
                                    modifier = Modifier.rotate(-(weather?.windDirectionDegrees?.toFloat() ?: 0.0f))
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                PlaceholderText(
                                    text = weather?.windDirection,
                                    placeholderSymbolLength = 7,
                                    fontSize = 20.sp
                                )
                            }
                            //TODO remove
                            if (weather?.windDirection == null) {
                                Text(text = "true")
                            }

                        }
                    }
                }
            }
        }
    }

    @Composable
    fun PlaceholderText(
        text: String?,
        placeholderSymbolLength: Int,
        modifier: Modifier = Modifier,
        fontStyle: FontStyle? = null,
        fontWeight: FontWeight? = null,
        fontSize: TextUnit = TextUnit.Unspecified
    ) {
        PlaceholderText(
            text = AnnotatedString(text ?: " ".repeat(placeholderSymbolLength)),
            fontStyle = fontStyle,
            fontSize = fontSize,
            fontWeight = fontWeight,
            modifier = modifier
        )
    }

    @Composable
    fun PlaceholderText(
        text: AnnotatedString,
        modifier: Modifier = Modifier,
        fontStyle: FontStyle? = null,
        fontWeight: FontWeight? = null,
        fontSize: TextUnit = TextUnit.Unspecified
    ) {
        Log.i(TAG, "placeholder: ${text.isBlank()}")
        Text(
            text = text,
            fontStyle = fontStyle,
            fontSize = fontSize,
            fontWeight = fontWeight,
            modifier = modifier/*.placeholder(
                visible = text.isBlank(),
                highlight = PlaceholderHighlight.shimmer()
            )*/
        )
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
                windDirectionDegrees = 0,
                icon = ""
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

