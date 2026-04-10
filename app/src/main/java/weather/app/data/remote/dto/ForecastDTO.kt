package weather.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ForecastDTO(
    val location: LocationDTO,
    val forecast: ForecastListDTO
) {
    @Serializable
    data class ForecastListDTO(
        @SerialName("forecastday")
        val forecastDay: List<ForecastDayDTO>
    )

    @Serializable
    data class ForecastDayDTO(
        val date: String,
        val day: DayDTO
    )

    @Serializable
    data class DayDTO(
        val avgTempC: Double,
        val condition: ConditionDTO
    )
}