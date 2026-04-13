package weather.app.storage

import android.content.Context
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.*
import org.junit.Test
import weather.app.models.Location
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Before

class SearchHistoryStorageTests {

    private lateinit var context: Context
    private lateinit var storage: SearchHistoryStorageImpl

    private val json = Json { ignoreUnknownKeys = true }

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()

        val file = context.preferencesDataStoreFile("search_history")
        if (file.exists()) file.delete()

        storage = SearchHistoryStorageImpl(context)
    }
    @Test
    fun loadHistory_returnsEmptyList_whenNoDataSaved() = runTest {
        val result = storage.loadHistory()
        assertTrue(result.isEmpty())
    }

    @Test
    fun saveToHistory_thenLoadHistory_returnsSavedLocation() = runTest {
        val location = Location("Poznań")

        storage.saveToHistory(location)
        val result = storage.loadHistory()

        assertEquals(1, result.size)
        assertEquals("Poznań", result.first().name)
    }

    @Test
    fun saveToHistory_movesExistingLocationToTop() = runTest {
        val location1 = Location("Poznań")
        val location2 = Location("Swarzędz")

        storage.saveToHistory(location1)
        storage.saveToHistory(location2)
        storage.saveToHistory(location1)

        val result = storage.loadHistory()

        assertEquals("Poznań", result.first().name)
        assertEquals("Swarzędz", result[1].name)
    }

    @Test
    fun saveToHistory_trimsHistoryTo10Items() = runTest {
        val items = (1..15).map {
            Location("Location $it")
        }

        items.forEach { storage.saveToHistory(it) }

        val result = storage.loadHistory()

        assertEquals(10, result.size)
        assertEquals("Location 15", result.first().name)
    }

    @Test
    fun saveLastLocation_thenLoadLastLocation_returnsCorrectValue() = runTest {
        val location = Location("Paris")

        storage.saveLastLocation(location)
        val result = storage.loadLastLocation()

        assertNotNull(result)
        assertEquals("Paris", result!!.name)
    }

    @Test
    fun loadLastLocation_returnsNull_whenNoDataSaved() = runTest {
        val result = storage.loadLastLocation()
        assertNull(result)
    }

    @Test
    fun loadLastLocation_returnsNull_whenJsonCorrupted() = runTest {
        val file = context.preferencesDataStoreFile("search_history")
        val corrupted = mapOf("last_location" to "{broken json")
        file.writeText(json.encodeToString(corrupted))

        val result = storage.loadLastLocation()

        assertNull(result)
    }
}
