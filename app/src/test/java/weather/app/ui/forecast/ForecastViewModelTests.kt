package weather.app.ui.forecast

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import weather.app.models.Condition
import weather.app.models.Forecast
import weather.app.models.location.ForecastLocation
import weather.app.repository.WeatherRepository
import weather.app.utils.Result

@OptIn(ExperimentalCoroutinesApi::class)
class ForecastViewModelTests {

    private val repository = mockk<WeatherRepository>()
    private lateinit var viewModel: ForecastViewModel


    @BeforeEach
    fun setUp() {
        viewModel = ForecastViewModel(repository)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadForecast sets forecast on success`() = runTest {
        // given
        val forecast = Forecast(
            forecastLocation = ForecastLocation(
                name = "Poznań",
                region = "",
                country = "Poland",
                lat = 52.4167,
                lon = 16.9667,
                timezone = "Europe/Warsaw",
                localTime = "2026-04-15 20:00"
            ),
            days = listOf(
                Forecast.ForecastDay(
                    date = "2026-04-13",
                    avgTempC = 8.1,
                    condition = Condition(
                        text = "Clear",
                        icon = "//cdn.weatherapi.com/weather/64x64/night/113.png",
                        code = 1000
                    )
                ),
                Forecast.ForecastDay(
                    date = "2026-04-14",
                    avgTempC = 6.4,
                    condition = Condition(
                        text = "Clear",
                        icon = "//cdn.weatherapi.com/weather/64x64/night/113.png",
                        code = 1000
                    )
                )
            ),
            current = Forecast.Current(
                temperatureC = 12.0,
                condition = Condition(
                    text = "Sunny",
                    icon = "//cdn.weatherapi.com/weather/64x64/day/113.png",
                    code = 1000
                ),
                windKph = 3.4,
                humidity = 60,
                feelsLikeC = 13.5,
                isDay = true
            )
        )

        coEvery { repository.getForecast("Poznań", 7) } returns Result.Success(forecast)

        // when
        viewModel.loadForecast("Poznań")

        // then
        viewModel.uiState.test {
            val state = awaitItem()
            assertFalse(state.isLoading)
            assertEquals(forecast, state.forecast)
            assertNull(state.error)
        }
    }

    @Test
    fun `loadForecast sets sets forecast to null on Failure`() = runTest {
        // given
        coEvery { repository.getForecast("Poznań", 7) } returns Result.Failure("Error message")

        // when
        viewModel.loadForecast("Poznań")

        // then
        viewModel.uiState.test {
            val state = awaitItem()
            assertFalse(state.isLoading)
            assertEquals("Error message", state.error)
            assertEquals(null, state.forecast)
        }
    }
}