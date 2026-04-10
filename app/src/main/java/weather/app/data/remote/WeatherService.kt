package weather.app.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import weather.app.data.remote.dto.CurrentWeatherDTO
import weather.app.data.remote.dto.ForecastDTO
import weather.app.data.remote.dto.LocationDTO

interface WeatherService {
    @GET("v1/current.json")
    suspend fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("q") location: String
    ): CurrentWeatherDTO

    @GET("v1/forecast.json")
    suspend fun getForecast(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("days") days: Int
    ): ForecastDTO

    @GET("v1/search.json")
    suspend fun searchLocation(
        @Query("key") apiKey: String,
        @Query("q") location: String
    ): List<LocationDTO>
}