package weather.app.repository

import weather.app.models.Forecast
import weather.app.models.location.SearchLocation
import weather.app.utils.Result

interface WeatherRepository {
    suspend fun getForecast(location: String, days: Int): Result<Forecast>
    suspend fun searchLocation(location: String): Result<List<SearchLocation>>
}
