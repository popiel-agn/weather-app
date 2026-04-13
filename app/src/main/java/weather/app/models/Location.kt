package weather.app.models

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val name: String,
    val region: String? = null,
    val country: String? = null,
    val localTime: String? = null,
    val lat: Double? = null,
    val lon: Double? = null,
)