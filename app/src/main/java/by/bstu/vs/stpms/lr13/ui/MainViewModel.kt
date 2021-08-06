package by.bstu.vs.stpms.lr13.ui

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import by.bstu.vs.stpms.lr13.R
import by.bstu.vs.stpms.lr13.data.model.Article
import by.bstu.vs.stpms.lr13.data.model.LocationCity
import by.bstu.vs.stpms.lr13.data.model.Weather
import by.bstu.vs.stpms.lr13.data.repository.NewsRepository
import by.bstu.vs.stpms.lr13.data.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {
    //TODO di
    var newsRepository = NewsRepository()
    var weatherRepository = WeatherRepository()

    val isRefreshing = MutableStateFlow(false)

    var weather by mutableStateOf(Weather())
    var articles: List<Article> by mutableStateOf(listOf())

    private val _city = MutableLiveData(LocationCity())
    val city: LiveData<LocationCity> = _city

    fun onCityChanged(newCity: LocationCity?) {
        newCity?.let {
            _city.value = it
        }
    }

    val weatherKey: String = application.getString(R.string.weather_key)
    val newsKey: String = application.getString(R.string.news_key)

    //TODO logs
    fun getWeather(language: String, units: String) {
        viewModelScope.launch {
            weather = weatherRepository.getWeather(
                city = city.value!!,
                appId = weatherKey,
                units = units,
                language = language
            )
        }
    }

    //TODO logs
    fun getArticles(country: String) {
        viewModelScope.launch {
            articles = newsRepository.getNews(
                appId = newsKey,
                country = country
            )
        }
    }

}