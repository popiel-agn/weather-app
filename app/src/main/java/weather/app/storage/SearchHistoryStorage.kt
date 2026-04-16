package weather.app.storage

import weather.app.models.location.Location

interface SearchHistoryStorage {
    suspend fun loadHistory(): List<Location>
    suspend fun saveToHistory(location: Location)
    suspend fun saveLastLocation(location: Location)
    suspend fun loadLastLocation(): Location?
}