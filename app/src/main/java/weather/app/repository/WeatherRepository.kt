package weather.app.repository

import weather.app.models.Location
import weather.app.models.Weather

interface WeatherRepository {

    suspend fun getCurrentWeather(apiKey: String, location: String): Weather

    suspend fun searchLocation(apiKey: String, location: String): List<Location>
}