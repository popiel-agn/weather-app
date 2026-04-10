package weather.app.data.mappers

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import weather.app.data.remote.dto.ConditionDTO
import weather.app.data.remote.dto.CurrentWeatherDTO
import weather.app.data.remote.dto.CurrentWeatherDTO.Current
import weather.app.data.remote.dto.LocationDTO

class WeatherMapperTests {
    
    @Test
    fun `CurrentWeatherDTO to Weather mapping`() {
        val currentWeatherDTO = CurrentWeatherDTO(
            location = LocationDTO(
                name = "Poznan",
                region = "",
                country = "Poland",
                lat = 52.4167,
                lon = 16.9667,
                localTime =  "2026-04-09 22:38"
            ),
            current = Current(
                tempC = 2.1,
                condition = ConditionDTO(
                    text = "Clear",
                    icon = "//cdn.weatherapi.com/weather/64x64/night/113.png"
                ),
                windKph = 13.7,
                humidity = 55,
                feelsLikeC = -1.6
            )
        )

        val weather = currentWeatherDTO.toDomain()

        assertEquals(currentWeatherDTO.location.name, weather.location.name)
        assertEquals(currentWeatherDTO.location.country, weather.location.country)
        assertEquals(null, weather.location.region)
        assertEquals(currentWeatherDTO.current.condition.text, weather.current.condition.text)
        assertEquals(currentWeatherDTO.current.tempC, weather.current.temperatureC, 0.0)
        assertEquals(currentWeatherDTO.current.windKph, weather.current.windKph, 0.0)
        assertEquals(currentWeatherDTO.current.humidity, weather.current.humidity)
        assertEquals(currentWeatherDTO.current.feelsLikeC, weather.current.feelsLikeC, 0.0)
    }
}