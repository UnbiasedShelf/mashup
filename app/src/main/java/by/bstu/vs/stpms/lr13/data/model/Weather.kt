package by.bstu.vs.stpms.lr13.data.model

data class Weather(
    val description: String?,
    val temperature: Double?,
    val pressure: Double?,
    val humidity: Double?,
    val windSpeed: Double?,
    val windDirection: Double?,
    val icon: String?
)