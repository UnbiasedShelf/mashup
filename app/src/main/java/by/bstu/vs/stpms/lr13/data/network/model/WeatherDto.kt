package by.bstu.vs.stpms.lr13.data.network.model

import com.google.gson.annotations.SerializedName

/**
 * DTO class of weather from API
 */
data class WeatherDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("weather")
    val weather: List<WeatherDescriptionDto>,
    @SerializedName("main")
    val main: MainDto,
    @SerializedName("wind")
    val wind: WindDto,
)
/**
 * DTO class of weather description values [description], [icon] from API
 */
data class WeatherDescriptionDto(
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String
)

/**
 * DTO class of weather description values [temperature], [humidity] from API
 */
data class MainDto(
    @SerializedName("temp")
    val temperature: String,
    @SerializedName("humidity")
    val humidity: String
)

/**
 * DTO class of weather wind description values [speed], [deg] from API
 */
data class WindDto(
    @SerializedName("speed")
    val speed: String,
    @SerializedName("deg")
    val deg: String,
)



