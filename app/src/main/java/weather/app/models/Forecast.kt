package weather.app.models

data class Forecast(
    val location: Location,
    val days: List<ForecastDay>
) {
    data class ForecastDay(
        val date: String,
        val avgTempC: Double,
        val condition: Condition
    )
}