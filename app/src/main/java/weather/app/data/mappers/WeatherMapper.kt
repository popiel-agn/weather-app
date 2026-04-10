package weather.app.data.mappers

import weather.app.data.remote.dto.CurrentWeatherDTO
import weather.app.models.Condition
import weather.app.models.Weather

fun CurrentWeatherDTO.toDomain(): Weather {
    return Weather(
        location = location.toDomain(),
        current = Weather.Current(
            temperatureC = current.tempC,
            condition = Condition(
                text = current.condition.text,
                icon = current.condition.icon
            ),
            windKph = current.windKph,
            humidity = current.humidity,
            feelsLikeC = current.feelsLikeC,
        )
    )
}
