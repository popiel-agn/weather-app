package weather.app.repository

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import weather.app.data.remote.WeatherService
import weather.app.data.remote.dto.ConditionDTO
import weather.app.data.remote.dto.ForecastDTO
import weather.app.data.remote.dto.LocationForecastDTO
import weather.app.utils.Result

class WeatherRepositoryImplTests {

    private val api = mockk<WeatherService>()

    private val apiKey = "TEST_KEY"
    private val repository = WeatherRepositoryImpl(api, apiKey)

    @Test
    fun `getForecast returns Success`() = runBlocking {
        // given
        val dto = ForecastDTO(
            location = LocationForecastDTO(
                name = "Poznan",
                region = "",
                country = "Poland",
                lat = 52.4167,
                lon = 16.9667,
                tz_id = "Europe/Warsaw",
                localtime = "2026-04-10 12:00",
            ),
            current = ForecastDTO.CurrentDTO(
                tempC = 5.3,
                condition = ConditionDTO("Clear", "//clear.png", 1000),
                windKph = 8.0,
                humidity = 70,
                feelsLikeC = 3.0,
                isDay = 1
            ),
            forecast = ForecastDTO.ForecastBlockDTO(
                forecastDay = listOf(
                    ForecastDTO.ForecastDayDTO(
                        date = "2026-04-10",
                        day = ForecastDTO.DayDTO(
                            avgTempC = 14.2,
                            condition = ConditionDTO("Sunny", "//sunny.png", 1004)
                        )
                    )
                )
            )
        )

        coEvery { api.getForecast(apiKey, "Poznan", 7) } returns dto

        // when
        val result = repository.getForecast("Poznan", 7)

        // then
        assertTrue(result is Result.Success)
    }

    @Test
    fun `getForecast returns Failure when API throws`() = runBlocking {
        // given
        coEvery { api.getForecast(apiKey, "Poznan", 7) } throws RuntimeException("API error")

        // when
        val result = repository.getForecast("Poznan", 7)

        // then
        assertTrue(result is Result.Failure)
    }
}
