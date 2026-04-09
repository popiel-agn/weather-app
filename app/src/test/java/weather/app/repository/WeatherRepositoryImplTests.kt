package weather.app.repository

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import weather.app.data.remote.WeatherService
import weather.app.data.remote.dto.Condition
import weather.app.data.remote.dto.CurrentWeatherDTO
import weather.app.data.remote.dto.CurrentWeatherDTO.Current
import weather.app.data.remote.dto.Location
import weather.app.utils.Result

class WeatherRepositoryImplTests {

    private val api = mock<WeatherService>()

    private val apiKey = "TEST_KEY"
    private val repository = WeatherRepositoryImpl(api, apiKey)

    @Test
    fun `getCurrentWeather returns Success`() = runBlocking {
        // Given
        val currentWeatherDTO = CurrentWeatherDTO(
            location = Location(
                name = "Poznan",
                region = "",
                country = "Poland",
                lat = 52.4167,
                lon = 16.9667,
                localTime = "2026-04-09 22:38"
            ),
            current = Current(
                tempC = 2.1,
                condition = Condition(
                    text = "Clear",
                    icon = "//cdn.weatherapi.com/weather/64x64/night/113.png"
                ),
                windKph = 13.7,
                humidity = 55,
                feelsLikeC = -1.6
            )
        )

        whenever(api.getCurrentWeather(apiKey = apiKey, location = "Poznan")).thenReturn(currentWeatherDTO)

        // When
        val result = repository.getCurrentWeather(apiKey = apiKey, location = "Poznan")

        // Then
        assertTrue(result is Result.Success)
    }

    @Test
    fun `getCurrentWeather returns Failure`() = runBlocking {
        // When
        val result = repository.getCurrentWeather(apiKey = "INCORRECT_API_KEY", location = "Poznan")

        // Then
        assertTrue(result is Result.Failure)
    }
}