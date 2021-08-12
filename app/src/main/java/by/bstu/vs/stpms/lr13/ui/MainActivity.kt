package by.bstu.vs.stpms.lr13.ui

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import by.bstu.vs.stpms.lr13.R
import by.bstu.vs.stpms.lr13.data.location.LocationService
import by.bstu.vs.stpms.lr13.data.util.isConnectedToNetwork
import by.bstu.vs.stpms.lr13.ui.theme.MashupTheme
import by.bstu.vs.stpms.lr13.ui.widget.ArticleWidget
import by.bstu.vs.stpms.lr13.ui.widget.NoConnectionWidget
import by.bstu.vs.stpms.lr13.ui.widget.WeatherWidget
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.util.*

//TODO network exception handling
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
                        },
                    )
                },
            ) {
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
                    var isConnected by remember {
                        mutableStateOf(false)
                    }

                    suspend fun fetchData() {
                        isConnected = isConnectedToNetwork(this@MainActivity)
                        if (isConnected) {
                            val location = LocationService.getLocation(this@MainActivity)
                            location?.let {
                                mainViewModel.fetchData(it)
                            }
                        } else {
                            Log.i(TAG, "fetchData: call without network!")
                        }
                    }
                    SwipeRefresh(
                        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
                        onRefresh = {
                            lifecycleScope.launchWhenStarted {
                                fetchData()
                            }
                        }) {


                        LaunchedEffect(true) {
                            fetchData()
                        }
                        if (isConnected) {
                            LazyColumn {
                                item {
                                    WeatherWidget(weather = mainViewModel.weather)
                                }
                                items(mainViewModel.news) { article ->
                                    ArticleWidget(article = article)
                                }
                            }
                        }
                        else {
                            NoConnectionWidget(onTryAgainButtonClicked = {
                                lifecycleScope.launchWhenStarted {
                                    fetchData()
                                }
                            })
                        }
                    }
                }
            }
        }


    }
}

