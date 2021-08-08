package by.bstu.vs.stpms.lr13.data.model

//TODO remove string to resources
data class Weather(
    val city: String,
    val description: String,
    val temperature: String,
    val temperatureUnits: String,
    val humidity: String,
    val windSpeed: String,
    val windDirectionDegrees: Int,
    val icon: String
) {
    val windDirection: String
    get () = when(windDirectionDegrees) {
        in 0..22, in 338..359 -> "W"
        in 23..67 -> "SW"
        in 68..112 -> "S"
        in 113..157 -> "SE"
        in 158..202 -> "E"
        in 203..247 -> "NE"
        in 248..292 -> "N"
        in 293..337 -> "NW"
        else -> "--"
    }
}