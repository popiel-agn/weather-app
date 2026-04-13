package weather.app.repository

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import weather.app.data.remote.WeatherService
import weather.app.data.remote.dto.ConditionDTO
import weather.app.data.remote.dto.CurrentWeatherDTO
import weather.app.data.remote.dto.CurrentWeatherDTO.Current
import weather.app.data.remote.dto.ForecastDTO
import weather.app.data.remote.dto.LocationDTO
import weather.app.utils.Result

class WeatherRepositoryImplTests {

    private val api = mockk<WeatherService>()

    private val apiKey = "TEST_KEY"
    private val repository = WeatherRepositoryImpl(api, apiKey)

    @Test
    fun `getCurrentWeather returns Success`() = runBlocking {
        // Given
        val currentWeatherDTO = CurrentWeatherDTO(
            location = LocationDTO(
                name = "Poznan",
                region = "",
                country = "Poland",
                lat = 52.4167,
                lon = 16.9667,
                localTime = "2026-04-09 22:38"
            ), current = Current(
                tempC = 2.1, condition = ConditionDTO(
                    text = "Clear", icon = "//cdn.weatherapi.com/weather/64x64/night/113.png"
                ), windKph = 13.7, humidity = 55, feelsLikeC = -1.6
            )
        )

        coEvery { api.getCurrentWeather(apiKey = apiKey, location = "Poznan") } returns currentWeatherDTO

        // When
        val result = repository.getCurrentWeather(location = "Poznan")

        // Then
        assertTrue(result is Result.Success)
    }

    @Test
    fun `getCurrentWeather returns Failure when api key is incorrect`() = runBlocking {
        // When
        coEvery { api.getCurrentWeather(apiKey = "INCORRECT_API_KEY", location = "Poznan") }

        val result = repository.getCurrentWeather(location = "Poznan")

        // Then
        assertTrue(result is Result.Failure)
    }

    @Test
    fun `getForecast() returns Success`() = runBlocking {
        // Given
        val forecastDTO = ForecastDTO(
            location = LocationDTO(
                name = "Poznan",
                region = "",
                country = "Poland",
                lat = 52.4167,
                lon = 16.9667,
                localTime = "2026-04-09 22:38"
            ), forecast = ForecastDTO.ForecastListDTO(
                listOf(
                    ForecastDTO.ForecastDayDTO(
                        date = "2026-04-10", day = ForecastDTO.DayDTO(
                            avgTempC = 6.2, condition = ConditionDTO(
                                text = "Clear",
                                icon = "//cdn.weatherapi.com/weather/64x64/night/113.png"
                            )
                        )
                    ), ForecastDTO.ForecastDayDTO(
                        date = "2026-04-11", day = ForecastDTO.DayDTO(
                            avgTempC = 4.9, condition = ConditionDTO(
                                text = "Clear",
                                icon = "//cdn.weatherapi.com/weather/64x64/night/113.png"
                            )
                        )
                    )
                )
            )
        )

        coEvery { api.getForecast(apiKey = apiKey, location = "Poznan", days = 2) } returns forecastDTO

        // When
        val result = repository.getForecast(location = "Poznan", days = 2)

        // Then
        assertTrue(result is Result.Success)
    }
}