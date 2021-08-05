package by.bstu.vs.stpms.lr13.data.network.model

import com.google.gson.annotations.SerializedName

data class WeatherDto(
    @SerializedName("weather")
    val description: List<WeatherDescriptionDto>?,
    @SerializedName("main")
    val main: MainDto?,
    @SerializedName("wind")
    val wind: WindDto?,
)

data class WeatherDescriptionDto(
    @SerializedName("description")
    val description: String?,
    @SerializedName("icon")
    val icon: String?
)

data class MainDto(
    @SerializedName("temp")
    val temperature: String?,
    @SerializedName("pressure")
    val pressure: String?,
    @SerializedName("humidity")
    val humidity: String?
)

data class WindDto(
    @SerializedName("speed")
    val speed: String?,
    @SerializedName("deg")
    val deg: String?,
)



