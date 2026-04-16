package weather.app.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LocationForecastDTO(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val tz_id: String,
    val localtime: String
)
