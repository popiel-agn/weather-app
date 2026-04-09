package weather.app.data.remote.dto
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherDTO(
    val location: Location,
    val current: Current
) {
    @Serializable
    data class Current(
        val tempC: Double,
        val condition: Condition,
        val windKph: Double,
        val humidity: Int,
        val feelsLikeC: Double
    )
}
