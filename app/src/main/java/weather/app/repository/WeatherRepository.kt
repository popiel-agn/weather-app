package weather.app.repository

import weather.app.models.Forecast
import weather.app.models.Location
import weather.app.models.Weather
import weather.app.utils.Result

interface WeatherRepository {

    suspend fun getCurrentWeather(apiKey: String, location: String): Result<Weather>

    suspend fun getForecast(apiKey: String, location: String, days: Int): Result<Forecast>

    suspend fun searchLocation(apiKey: String, location: String): Result<List<Location>>
}