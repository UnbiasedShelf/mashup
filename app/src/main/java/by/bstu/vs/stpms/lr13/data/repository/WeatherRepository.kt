package by.bstu.vs.stpms.lr13.data.repository

import android.location.Location
import android.util.Log
import by.bstu.vs.stpms.lr13.data.model.Article
import by.bstu.vs.stpms.lr13.data.model.MeasureUnits
import by.bstu.vs.stpms.lr13.data.model.Weather
import by.bstu.vs.stpms.lr13.data.network.model.WeatherDto
import by.bstu.vs.stpms.lr13.data.network.ApiWeather
import by.bstu.vs.stpms.lr13.data.network.NetworkService
import by.bstu.vs.stpms.lr13.data.toWeather

/**
 * Repository of weather
 */
class WeatherRepository {
    val TAG = "WeatherRepository"

    val weatherApi: ApiWeather = NetworkService.weatherService()

    /**
     * Receives [WeatherDto] from [ApiWeather] and maps it to [Weather]
     * @param location The current device location
     * @param appId The application key for weather API
     * @param units The measure units of response object. Possible values: "imperial", "metric", "standard"
     * @param language The language of response object
     * @return [Weather]
     */
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