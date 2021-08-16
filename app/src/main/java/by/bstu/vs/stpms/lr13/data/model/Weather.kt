package by.bstu.vs.stpms.lr13.data.model

/**
 * Data class that contains info about weather
 * @property city The city of the weather
 * @property description The short description of the weather
 * @property temperature The temperature value in [measureUnits]
 * @property humidity The humidity value
 * @property windSpeed The wind speed value
 * @property measureUnits The measure units that used for other values
 * @property windDirectionDegrees The wind direction in degrees in range 0..359
 * @property icon The icon code from API
 * @property windDirection The get only property that returns string equivalent of [windDirectionDegrees].
 * Possible values: W, SW, S, SE, E, NE, N, NW, --
 */
data class Weather(
    val city: String,
    val description: String,
    val temperature: Double,
    val humidity: Int,
    val windSpeed: Double,
    val measureUnits: MeasureUnits,
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