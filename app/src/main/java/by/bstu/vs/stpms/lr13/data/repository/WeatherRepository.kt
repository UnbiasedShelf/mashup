package by.bstu.vs.stpms.lr13.data.repository

import android.util.Log
import by.bstu.vs.stpms.lr13.data.model.LocationCity
import by.bstu.vs.stpms.lr13.data.model.Weather
import by.bstu.vs.stpms.lr13.data.network.ApiWeather
import by.bstu.vs.stpms.lr13.data.network.NetworkService
import by.bstu.vs.stpms.lr13.data.toWeather

class WeatherRepository {
    val TAG = "WeatherRepository"
    //TODO di
    val weatherApi: ApiWeather = NetworkService.weatherService()

    suspend fun getWeather(city: LocationCity, appId: String, units: String, language: String): Weather {
        val weatherDto = weatherApi.getWeatherByCoords(
            latitude = city.latitude!!,
            longitude = city.longitude!!,
            appId = appId,
            units = units,
            language = language
        )
        val weather = weatherDto.toWeather()
        Log.i(TAG, "getWeather: return $weather")
        return weather
    }
}