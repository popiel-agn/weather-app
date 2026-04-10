package weather.app.data.remote.dto
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherDTO(
    val location: LocationDTO,
    val current: Current
) {
    @Serializable
    data class Current(
        val tempC: Double,
        val condition: ConditionDTO,
        val windKph: Double,
        val humidity: Int,
        val feelsLikeC: Double
    )
}
