package weather.app.data.mappers

import weather.app.data.remote.dto.ForecastDTO
import weather.app.models.Condition
import weather.app.models.Forecast

fun ForecastDTO.toStored(): Forecast {
    return Forecast(
        searchLocation = location.toStored(),
        current = Forecast.Current(
            temperatureC = current.tempC,
            condition = Condition(
                text = current.condition.text,
                icon = current.condition.icon,
                code = current.condition.code
            ),
            windKph = current.windKph,
            humidity = current.humidity,
            feelsLikeC = current.feelsLikeC,
            isDay = current.isDay == 1
        ),
        days = forecast.forecastDay.map { it.toStored() }
    )
}

fun ForecastDTO.ForecastDayDTO.toStored(): Forecast.ForecastDay {
    return Forecast.ForecastDay(
        date = date,
        avgTempC = day.avgTempC,
        condition = Condition(
            text = day.condition.text,
            icon = day.condition.icon,
            code = day.condition.code
        )
    )
}
