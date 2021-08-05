package by.bstu.vs.stpms.lr13.ui

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import by.bstu.vs.stpms.lr13.R
import by.bstu.vs.stpms.lr13.data.model.Weather
import by.bstu.vs.stpms.lr13.data.network.NetworkService
import by.bstu.vs.stpms.lr13.data.network.model.WeatherDto
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {
    //TODO di
    var apiNews = NetworkService.newsService()
    var apiWeather = NetworkService.weatherService()

    var weather by mutableStateOf()


    private val _city = MutableLiveData("")
    val city: LiveData<String> = _city

    fun onCityChanged(newCity: String) {
        _city.value = newCity
    }

    val weatherKey: String = application.getString(R.string.weather_key)
    val newsKey: String = application.getString(R.string.news_key)

    fun getWeather(): Weather {
        viewModelScope.launch {
            apiWeather
        }
    }

}