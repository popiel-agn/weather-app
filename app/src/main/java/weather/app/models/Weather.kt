package weather.app.models

data class Weather(
    val location: Location,
    val current: Current
) {
    data class Current(
        val temperatureC: Double,
        val condition: Condition,
        val windKph: Double,
        val humidity: Int,
        val feelsLikeC: Double
    )
}