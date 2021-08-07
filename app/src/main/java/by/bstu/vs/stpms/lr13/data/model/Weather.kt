package by.bstu.vs.stpms.lr13.data.model

data class Weather(
    val city: String = "Undefined",
    val description: String = "Undefined",
    val temperature: String = "-//-",
    val temperatureUnits: String = "°C",
    val humidity: String = "-//- %",
    val windSpeed: String = "-//- m/s",
    val windDirectionDegrees: Int = 0,
    val windDirection: String = "-//-",
    val icon: String = ""
)