package weather.app.repository

import weather.app.data.mappers.toDomain
import weather.app.data.remote.WeatherService
import weather.app.models.Location
import weather.app.models.Weather
import weather.app.utils.Result

class WeatherRepositoryImpl(
    private val weatherService: WeatherService,
    private val apiKey: String
) : WeatherRepository {

    override suspend fun getCurrentWeather(
        apiKey: String,
        location: String
    ): Result<Weather> {
        return try {
            val currentWeatherResponse = weatherService.getCurrentWeather(apiKey, location).toDomain()
            Result.Success(currentWeatherResponse)
        } catch (e: Exception) {
            Result.Failure("Couldn't fetch current weather for location: $location", e)
        }
    }

    override suspend fun searchLocation(
        apiKey: String,
        location: String
    ): Result<List<Location>> {
        return try {
            val searchLocationResponse = weatherService.searchLocation(apiKey, location).map { it.toDomain() }
            Result.Success(searchLocationResponse)
        } catch (e: Exception) {
            Result.Failure("Couldn't fetch autocomplete results for location: $location", e)
        }
    }
}
