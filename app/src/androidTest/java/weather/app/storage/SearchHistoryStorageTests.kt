package weather.app.storage

import android.content.Context
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import weather.app.models.location.Location

class SearchHistoryStorageTests {

    private lateinit var context: Context
    private lateinit var storage: SearchHistoryStorageImpl

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
        val location = Location(
            name = "Poznan",
            region = "",
            country = "Country",
            lat = 40.7128,
            lon = -74.006,
            id = 1976891,
            url = "poznan-poland"
        )

        storage.saveToHistory(location)
        val result = storage.loadHistory()

        assertEquals(1, result.size)
        assertEquals("Poznan", result.first().name)
    }

    @Test
    fun saveToHistory_movesExistingLocationToTop() = runTest {
        val loc1 = Location(1234, "Poznan", "", "Poland", 1.0, 1.0, "poznan")
        val loc2 = Location(2345,"Swarzedz", "", "Poland", 2.0, 2.0, "swarzedz")

        storage.saveToHistory(loc1)
        storage.saveToHistory(loc2)
        storage.saveToHistory(loc1)

        val result = storage.loadHistory()

        assertEquals("Poznan", result[0].name)
        assertEquals("Swarzedz", result[1].name)
    }

    @Test
    fun saveToHistory_trimsHistoryTo10Items() = runTest {
        val items = (1..15).map {
            Location(it, "Location $it", "", "PL", 0.0, 0.0,"url-$it")
        }

        items.forEach { storage.saveToHistory(it) }

        val result = storage.loadHistory()

        assertEquals(10, result.size)
        assertEquals("Location 15", result.first().name)
    }

    @Test
    fun saveLastLocation_thenLoadLastLocation_returnsCorrectValue() = runTest {
        val location = Location(1234, "Paris", "", "France", 1.0, 1.0, "paris")

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

        val corruptedJson = """{"last_location": "{broken json"}"""
        file.writeText(corruptedJson)

        val result = storage.loadLastLocation()

        assertNull(result)
    }
}