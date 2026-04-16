package weather.app.data.mappers

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import weather.app.data.remote.dto.ConditionDTO
import weather.app.data.remote.dto.ForecastDTO
import weather.app.data.remote.dto.LocationForecastDTO

class ForecastMapperTests {

    @Test
    fun `ForecastDTO to Forecast mapping`() {
        val dto = ForecastDTO(
            location = LocationForecastDTO(
                name = "Poznan",
                region = "",
                country = "Poland",
                lat = 52.4167,
                lon = 16.9667,
                tz_id = "Europe/Warsaw",
                localtime = "2026-04-10 12:00"
            ),
            current = ForecastDTO.CurrentDTO(
                tempC = 12.5,
                condition = ConditionDTO("Cloudy", "cloudy.png", 1000),
                windKph = 10.0,
                humidity = 60,
                feelsLikeC = 11.0,
                isDay = 1
            ),
            forecast = ForecastDTO.ForecastBlockDTO(
                forecastDay = listOf(
                    ForecastDTO.ForecastDayDTO(
                        date = "2026-04-10",
                        day = ForecastDTO.DayDTO(
                            avgTempC = 14.2,
                            condition = ConditionDTO("Sunny", "sunny.png", 1000)
                        )
                    ),
                    ForecastDTO.ForecastDayDTO(
                        date = "2026-04-11",
                        day = ForecastDTO.DayDTO(
                            avgTempC = 9.8,
                            condition = ConditionDTO("Rain", "rain.png", 1000)
                        )
                    )
                )
            )
        )

        val forecast = dto.toStored()

        assertEquals(dto.location.name, forecast.forecastLocation.name)
        assertEquals(dto.location.country, forecast.forecastLocation.country)

        assertEquals(dto.current.isDay == 1, forecast.current.isDay)

        assertEquals(2, forecast.days.size)

        val first = forecast.days[0]
        assertEquals("2026-04-10", first.date)
        assertEquals(14.2, first.avgTempC, 0.0)
        assertEquals("Sunny", first.condition.text)
        assertEquals("sunny.png", first.condition.icon)

        val second = forecast.days[1]
        assertEquals("2026-04-11", second.date)
        assertEquals(9.8, second.avgTempC, 0.0)
        assertEquals("Rain", second.condition.text)
        assertEquals("rain.png", second.condition.icon)
    }
}
