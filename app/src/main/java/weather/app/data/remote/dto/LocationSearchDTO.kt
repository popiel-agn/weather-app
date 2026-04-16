package weather.app.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LocationSearchDTO(
    val id: Int? = null,
    val name: String,
    val region: String = "",
    val country: String,
    val lat: Double,
    val lon: Double,
    val url: String
)
