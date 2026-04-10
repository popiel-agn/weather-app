package weather.app.models

data class Location(
    val name: String,
    val region: String?,
    val country: String,
    val localTime: String? = null,
    val lat: Double,
    val lon: Double,
)