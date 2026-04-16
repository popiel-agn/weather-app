package weather.app.models.location

import kotlinx.serialization.Serializable

// Model used for storage
@Serializable
data class StoredLocation(
    val id: Int?,
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val url: String
)
