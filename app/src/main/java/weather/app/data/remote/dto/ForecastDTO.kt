package weather.app.data.remote.dto

import kotlinx.serialization.Serializable


@Serializable
data class ForecastDTO(
    val location: Location,
    val forecast: Forecast
) {
    @Serializable
    data class Forecast(
        val forecastDay: List<ForecastDay>
    )

    @Serializable
    data class ForecastDay(
        val date: String,
        val day: Day
    )

    @Serializable
    data class Day(
        val avgTempC: Double,
        val condition: Condition
    )
}