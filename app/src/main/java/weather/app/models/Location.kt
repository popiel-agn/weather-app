package weather.app.models

data class Location(
    val name: String,
    val region: String?,
    val country: String,
    val lat: Double,
    val lon: Double,
)