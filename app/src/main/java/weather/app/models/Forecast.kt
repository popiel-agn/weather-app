package weather.app.models

import weather.app.models.location.ForecastLocation

data class Forecast(
    val searchLocation: ForecastLocation,
    val current: Current,
    val days: List<ForecastDay>
) {
    data class Current(
        val temperatureC: Double,
        val condition: Condition,
        val windKph: Double,
        val humidity: Int,
        val feelsLikeC: Double,
        val isDay: Boolean
    )
    data class ForecastDay(
        val date: String,
        val avgTempC: Double,
        val condition: Condition
    )
}