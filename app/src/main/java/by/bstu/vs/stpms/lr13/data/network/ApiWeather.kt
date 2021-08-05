package by.bstu.vs.stpms.lr13.data.network

import by.bstu.vs.stpms.lr13.data.network.model.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiWeather {
    @GET("data/2.5/weather")
    suspend fun getWeatherByCoords(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appId") appId: String,
        @Query("units") units: String,
        @Query("lang") language: String,
    ): WeatherDto
}