package weather.app.data.mappers

import weather.app.data.remote.dto.ForecastDTO
import weather.app.models.Condition
import weather.app.models.Forecast

fun ForecastDTO.toDomain(): Forecast {
    return Forecast(
        location = location.toDomain(),
        days = forecast.forecastDay.map { it.toDomain() }
    )
}

fun ForecastDTO.ForecastDayDTO.toDomain(): Forecast.ForecastDay {
    return Forecast.ForecastDay(
        date = date,
        avgTempC = day.avgTempC,
        condition = Condition(
            text = day.condition.text,
            icon = day.condition.icon
        )
    )
}