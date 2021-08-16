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
import by.bstu.vs.stpms.lr13.ui.values.paddingSize
import by.bstu.vs.stpms.lr13.ui.values.theme.MashupTheme
import by.bstu.vs.stpms.lr13.ui.widget.ArticleWidget
import by.bstu.vs.stpms.lr13.ui.widget.NoConnectionWidget
import by.bstu.vs.stpms.lr13.ui.widget.NoPermissionWidget
import by.bstu.vs.stpms.lr13.ui.widget.WeatherWidget
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
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
                        NoPermissionWidget(onButtonClicked = {
                            permissionState.launchPermissionRequest()
                        })
                    },
                    permissionNotAvailableContent = {
                        NoPermissionWidget(onButtonClicked = {
                            permissionState.launchPermissionRequest()
                        })
                    }
                ) {
                    var isConnected by remember {
                        mutableStateOf(true)
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
                                Log.i(TAG, "MainScreen: on refresh fetchData() call")
                                fetchData()
                            }
                        }
                    ) {

                        LaunchedEffect(true) {
                            Log.i(TAG, "MainScreen: initial fetchData() call")
                            fetchData()
                        }
                        if (isConnected) {
                            LazyColumn(
                                contentPadding = PaddingValues(vertical = paddingSize),
                                verticalArrangement = Arrangement.spacedBy(paddingSize)
                            ) {
                                item {
                                    WeatherWidget(weather = mainViewModel.weather)
                                }
                                items(mainViewModel.news) { article ->
                                    ArticleWidget(article = article)
                                }
                            }
                        }
                        else {
                            NoConnectionWidget(
                                onTryAgainButtonClicked = {
                                    lifecycleScope.launchWhenStarted {
                                        Log.i(TAG, "MainScreen: on try again button clicked fetchData() call")
                                        fetchData()
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

