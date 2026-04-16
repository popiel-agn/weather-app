package weather.app.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ConditionDTO(
    val text: String,
    val icon: String,
    val code: Int
)
