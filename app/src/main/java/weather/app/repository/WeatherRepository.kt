package weather.app.repository

import weather.app.models.Forecast
import weather.app.models.Location
import weather.app.models.Weather
import weather.app.utils.Result

interface WeatherRepository {

    suspend fun getCurrentWeather(location: String): Result<Weather>

    suspend fun getForecast(location: String, days: Int): Result<Forecast>

    suspend fun searchLocation(location: String): Result<List<Location>>
}