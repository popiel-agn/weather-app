package weather.app.ui.search

import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import weather.app.data.mappers.toSearch
import weather.app.models.location.Location
import weather.app.models.location.SearchLocation
import weather.app.repository.WeatherRepository
import weather.app.storage.SearchHistoryStorage
import weather.app.utils.Result

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTests {

    private val repository = mockk<WeatherRepository>()

    private val storage = mockk<SearchHistoryStorage>()
    private lateinit var viewModel: SearchViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        coEvery { storage.loadHistory() } returns emptyList()
        coEvery { storage.loadLastLocation() } returns null

        viewModel = SearchViewModel(
            repository,
            storage
        )
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // onQueryChange

    @Test
    fun `onQueryChange loads history when query is blank`() = runTest {
        // when
        viewModel.onQueryChange("")

        // then
        assertTrue(viewModel.uiState.value.history.isEmpty())
        coVerify { storage.loadHistory() }
    }

    @Test
    fun `onQueryChange loads suggestions when query is not blank`() = runTest {
        // given
        val suggestions = listOf(
            SearchLocation(
                id = 1234,
                name = "Nowy Targ",
                country = "Poland",
                lat = 12.345,
                lon = 45.678,
                url = "poland-nowy-targ"
            ),
            SearchLocation(
                id = 2345,
                name = "Nowy Sącz",
                country = "Poland",
                lat = 12.345,
                lon = 12.345,
                url = "poland-nowy-sacz"
            ),
        )
        coEvery { repository.searchLocation("Now") } returns Result.Success(suggestions)

        // when
        viewModel.onQueryChange("Now")
        advanceTimeBy(500)

        // then
        assertTrue(viewModel.uiState.value.suggestions.containsAll(suggestions))
    }

    // searchLocation
    @Test
    fun `searchLocation sets error on Failure`() = runTest {
        // given
        coEvery { repository.searchLocation("Poz") } returns Result.Failure("Error")

        // when
        viewModel.onQueryChange("Poz")
        advanceTimeBy(500)

        // then
        assertTrue(viewModel.uiState.value.error == "Error")
        assertTrue(viewModel.uiState.value.suggestions.isEmpty())
    }

    // onLocationSelected
    @Test
    fun `onLocationSelected saves location to history and last location`() = runTest {
        // given
        val location = Location(
            name = "Poznan",
            region = "",
            country = "Country",
            lat = 40.7128,
            lon = -74.006,
            id = 1976891,
            url = "poznan-poland"
        )

        coEvery { storage.loadHistory() } returns emptyList()
        coEvery { storage.loadLastLocation() } returns null

        coEvery { storage.saveToHistory(location) } just Runs
        coEvery { storage.saveLastLocation(location) } just Runs

        // when
        viewModel.onLocationSelected(location.toSearch())
        runCurrent()

        // then
        coVerify { storage.saveToHistory(location) }
        coVerify { storage.saveLastLocation(location) }
    }

    @Test
    fun `init loads history and last location`() = runTest {
        coEvery { storage.loadHistory() } returns emptyList()
        coEvery { storage.loadLastLocation() } returns null

        SearchViewModel(repository, storage)

        coVerify { storage.loadHistory() }
        coVerify { storage.loadLastLocation() }
    }
}