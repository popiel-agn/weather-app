package weather.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastDTO(
    val location: LocationForecastDTO,
    val current: CurrentDTO,
    val forecast: ForecastBlockDTO
) {
    @Serializable
    data class CurrentDTO(
        @SerialName("temp_c")
        val tempC: Double,
        val condition: ConditionDTO,
        @SerialName("wind_kph")
        val windKph: Double,
        val humidity: Int,
        @SerialName("feelslike_c")
        val feelsLikeC: Double,
        @SerialName("is_day")
        val isDay: Int
    )

    @Serializable
    data class ForecastBlockDTO(
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
        @SerialName("avgtemp_c")
        val avgTempC: Double,
        val condition: ConditionDTO
    )
}
