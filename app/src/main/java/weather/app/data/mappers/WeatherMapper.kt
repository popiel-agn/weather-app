package weather.app.data.mappers

import weather.app.data.remote.dto.CurrentWeatherDTO
import weather.app.models.Weather

fun CurrentWeatherDTO.toDomain(): Weather {
    return Weather(
        location = Weather.Location(
            name = location.name,
            country = location.country,
            localTime = location.localTime,
            region = location.region.ifEmpty { null }
        ),
        current = Weather.Current(
            temperatureC = current.tempC,
            condition = Weather.Condition(
                text = current.condition.text,
                icon = current.condition.icon
            ),
            windKph = current.windKph,
            humidity = current.humidity,
            feelsLikeC = current.feelsLikeC,
        )
    )
}
