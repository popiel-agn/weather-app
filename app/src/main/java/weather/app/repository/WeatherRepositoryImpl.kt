package weather.app.repository

import weather.app.data.remote.WeatherService
import weather.app.models.Location
import weather.app.models.Weather

class WeatherRepositoryImpl(
    private val weatherService: WeatherService,
    private val apiKey: String
) : WeatherRepository {

    override suspend fun getCurrentWeather(
        apiKey: String,
        location: String
    ): Weather {
        TODO("Not yet implemented")
    }

    override suspend fun searchLocation(
        apiKey: String,
        location: String
    ): List<Location> {
        TODO("Not yet implemented")
    }
}
