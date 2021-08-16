package by.bstu.vs.stpms.lr13.data.network

import by.bstu.vs.stpms.lr13.data.network.model.NewsDto
import by.bstu.vs.stpms.lr13.data.network.model.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface that provides methods for weather API
 */
interface ApiWeather {
    /**
     * Returns [WeatherDto] from API
     * @param latitude The current device latitude
     * @param longitude The current device longitude
     * @param appId The application key for weather API
     * @param units The measure units of response object. Possible values: "imperial", "metric", "standard"
     * @param language The language of response object
     * @return [WeatherDto]
     */
    @GET("data/2.5/weather")
    suspend fun getWeatherByCoords(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appId") appId: String,
        @Query("units") units: String,
        @Query("lang") language: String,
    ): WeatherDto
}