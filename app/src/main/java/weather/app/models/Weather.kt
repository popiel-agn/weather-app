package weather.app.models

data class Weather(
    val location: Location,
    val current: Current
) {
    data class Location(
        val name: String,
        val country: String,
        val localTime: String
    )

    data class Current(
        val temperatureC: Double,
        val condition: Condition,
        val windKph: Double,
        val humidity: Int,
        val feelsLikeC: Double
    )

    data class Condition(
        val text: String,
        val icon: String
    )
}