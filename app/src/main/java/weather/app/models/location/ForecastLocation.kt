package weather.app.models.location

// Model used for forecast
data class ForecastLocation(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val localTime: String,
)
