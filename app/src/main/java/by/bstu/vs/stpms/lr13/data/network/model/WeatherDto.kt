package by.bstu.vs.stpms.lr13.data.network.model

import com.google.gson.annotations.SerializedName

//TODO rewrite with dto
class WeatherDto {

    @SerializedName("weather")
    var description: List<WeatherDescription>? = null
    @SerializedName("main")
    var main: Main? = null
    @SerializedName("wind")
    var wind: Wind? = null


    class WeatherDescription {
        @SerializedName("description")
        var description: String? = null
    }
    class Main {
        @SerializedName("temp")
        var temperature: String? = null
        @SerializedName("pressure")
        var pressure: String? = null
        @SerializedName("humidity")
        var humidity: String? = null
    }
    class Wind {
        @SerializedName("speed")
        var speed: String? = null
    }
}