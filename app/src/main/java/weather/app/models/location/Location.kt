package weather.app.models.location

// Domain model for Location
data class Location(
    val id: Int?,
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val url: String
)