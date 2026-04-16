package weather.app.models.location

import kotlinx.serialization.Serializable

// Model used for search queries
@Serializable
data class SearchLocation(
    val id: Int? = null,
    val name: String,
    val region: String = "",
    val country: String,
    val lat: Double,
    val lon: Double,
    val url: String
)