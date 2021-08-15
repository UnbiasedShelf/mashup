package by.bstu.vs.stpms.lr13.data.repository

import android.location.Location
import android.util.Log
import by.bstu.vs.stpms.lr13.data.model.MeasureUnits
import by.bstu.vs.stpms.lr13.data.model.Weather
import by.bstu.vs.stpms.lr13.data.network.ApiWeather
import by.bstu.vs.stpms.lr13.data.network.NetworkService
import by.bstu.vs.stpms.lr13.data.toWeather

class WeatherRepository {
    val TAG = "WeatherRepository"

    val weatherApi: ApiWeather = NetworkService.weatherService()

    suspend fun getWeather(location: Location, appId: String, units: MeasureUnits, language: String): Weather {
        val weatherDto = weatherApi.getWeatherByCoords(
            latitude = location.latitude,
            longitude = location.longitude,
            appId = appId,
            units = units.value,
            language = language
        )
        val weather = weatherDto.toWeather(units)
        Log.i(TAG, "getWeather: return $weather")
        return weather
    }
}