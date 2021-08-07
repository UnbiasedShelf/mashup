package by.bstu.vs.stpms.lr13.data.model

enum class MeasureUnits(
    val value: String,
    val temperatureUnits: String,
    val speedUnits: String
) {
    METRIC("metric", "°C", "m/s"),
    IMPERIAL("imperial", "°F", "mph")
}