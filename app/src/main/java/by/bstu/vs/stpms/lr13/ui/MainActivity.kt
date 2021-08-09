package by.bstu.vs.stpms.lr13.ui

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import by.bstu.vs.stpms.lr13.R
import by.bstu.vs.stpms.lr13.data.location.LocationService
import by.bstu.vs.stpms.lr13.data.model.Article
import by.bstu.vs.stpms.lr13.data.model.MeasureUnits
import by.bstu.vs.stpms.lr13.data.model.Weather
import by.bstu.vs.stpms.lr13.data.util.units
import by.bstu.vs.stpms.lr13.ui.compose.ArticleWidget
import by.bstu.vs.stpms.lr13.ui.compose.WeatherWidget
import by.bstu.vs.stpms.lr13.ui.theme.MashupTheme
import coil.imageLoader
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
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
                    LazyColumn {
                        item {
                            LaunchedEffect(true) {
                                updateUI(mainViewModel)
                            }
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

                                WeatherWidget(
                                    weather = mainViewModel.weather
                                )
                            }
                        }
                        items(mainViewModel.news) { article ->
                            ArticleWidget(article = article)
                        }
                    }
                }
            }
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
        //TODO country from location not locale
        mainViewModel.getArticles(locale.country)

    }
}

