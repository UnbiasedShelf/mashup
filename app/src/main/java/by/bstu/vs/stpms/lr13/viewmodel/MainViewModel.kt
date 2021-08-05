package by.bstu.vs.stpms.lr13.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import by.bstu.vs.stpms.lr13.R
import by.bstu.vs.stpms.lr13.data.network.NetworkService

class MainViewModel(application: Application): AndroidViewModel(application) {
    //TODO di
    var apiNews = NetworkService.newsService()
    var apiWeather = NetworkService.weatherService()


    private val _city = MutableLiveData("")
    val city: LiveData<String> = _city

    fun onCityChanged(newCity: String) {
        _city.value = newCity
    }

    val weatherKey: String = application.getString(R.string.weather_key)
    val newsKey: String = application.getString(R.string.news_key)


}