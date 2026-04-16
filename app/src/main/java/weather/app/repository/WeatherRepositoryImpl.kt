package weather.app.repository

import weather.app.data.mappers.toStored
import weather.app.data.remote.WeatherService
import weather.app.models.Forecast
import weather.app.models.location.SearchLocation
import weather.app.utils.Result
import javax.inject.Inject
import javax.inject.Named

class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
    @Named("apiKey") private val apiKey: String
) : WeatherRepository {

    override suspend fun getForecast(
        location: String,
        days: Int
    ): Result<Forecast> {
        return try {
            val forecastResponse = weatherService.getForecast(apiKey, location, days).toStored()
            Result.Success(forecastResponse)
        } catch (e: Exception) {
            Result.Failure("Couldn't fetch forecast for location: $location", e)
        }
    }

    override suspend fun searchLocation(
        location: String
    ): Result<List<SearchLocation>> {
        return try {
            val searchLocationResponse = weatherService.searchLocation(apiKey, location).map { it.toStored() }
            Result.Success(searchLocationResponse)
        } catch (e: Exception) {
            Result.Failure("Couldn't fetch autocomplete results for location: $location", e)
        }
    }
}
