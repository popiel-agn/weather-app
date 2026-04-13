package weather.app.storage


import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json
import weather.app.models.Location

private const val TAG = "SearchHistoryStorageImpl"
private val Context.dataStore by preferencesDataStore(name = "search_history")
class SearchHistoryStorageImpl(
    private val context: Context
) : SearchHistoryStorage {

    private val HISTORY_KEY = stringPreferencesKey("history")
    private val LAST_LOCATION_KEY = stringPreferencesKey("last_location")

    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun loadHistory(): List<Location> {
        val prefs = context.dataStore.data.first()
        val jsonString = prefs[HISTORY_KEY] ?: return emptyList()

        return try {
            json.decodeFromString(jsonString)
        } catch (e: Exception) {
            Log.e(TAG, "loadHistory: ", e)
            emptyList()
        }
    }

    override suspend fun saveToHistory(location: Location) {
        val current = loadHistory().toMutableList()

        current.removeAll { it.name == location.name }
        current.add(0, location)

        val trimmedHistory = current.take(10)

        context.dataStore.edit { prefs ->
            prefs[HISTORY_KEY] = json.encodeToString(trimmedHistory)
        }
    }

    override suspend fun saveLastLocation(location: Location) {
        context.dataStore.edit { prefs ->
            prefs[LAST_LOCATION_KEY] = json.encodeToString(location)
        }
    }

    override suspend fun loadLastLocation(): Location? {
        val prefs = context.dataStore.data.first()
        val jsonString = prefs[LAST_LOCATION_KEY] ?: return null

        return try {
            json.decodeFromString(jsonString)
        } catch (e: Exception) {
            Log.e(TAG, "loadLastLocation: ", e)
            null
        }
    }
}