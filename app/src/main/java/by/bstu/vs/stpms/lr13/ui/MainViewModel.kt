package by.bstu.vs.stpms.lr13.ui

import android.app.Application
import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import by.bstu.vs.stpms.lr13.R
import by.bstu.vs.stpms.lr13.data.model.Article
import by.bstu.vs.stpms.lr13.data.model.MeasureUnits
import by.bstu.vs.stpms.lr13.data.model.Weather
import by.bstu.vs.stpms.lr13.data.repository.NewsRepository
import by.bstu.vs.stpms.lr13.data.repository.WeatherRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val TAG = "MainViewModel"
    //TODO di
    var newsRepository = NewsRepository()
    var weatherRepository = WeatherRepository()

    val isRefreshing = MutableStateFlow(false)

    var weather by mutableStateOf<Weather?>(null)
    var articles: List<Article> by mutableStateOf(listOf())

    val weatherKey: String = application.getString(R.string.weather_key)
    val newsKey: String = application.getString(R.string.news_key)

    fun getWeather(location: Location, language: String, units: MeasureUnits) {
        viewModelScope.launch {
            weather = null
            delay(3000)
            weather = weatherRepository.getWeather(
                location = location,
                appId = weatherKey,
                units = units,
                language = language
            )
        }
    }

    fun getArticles(country: String) {
        viewModelScope.launch {
            articles = newsRepository.getNews(
                appId = newsKey,
                country = country
            )
        }
    }

}