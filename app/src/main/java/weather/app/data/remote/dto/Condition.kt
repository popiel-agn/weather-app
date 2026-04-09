package weather.app.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class Condition(
    val text: String,
    val icon: String
)
