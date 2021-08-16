package by.bstu.vs.stpms.lr13.data.model

/**
 * Measure units that can be used for [Weather].
 * Possible values: [METRIC], [IMPERIAL]
 *
 * @property value The name of instance
 * @property temperatureUnits The temperature units
 */
enum class MeasureUnits(
    val value: String,
    val temperatureUnits: String
) {
    /**
     * Metric measure units
     */
    METRIC("metric", "°C"),
    /**
     * Imperial measure units
     */
    IMPERIAL("imperial", "°F")
}