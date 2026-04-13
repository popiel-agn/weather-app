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
import weather.app.models.Location
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
        val suggestions = listOf(Location("Nowy Targ"), Location("Nowy Sącz"))
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
        val location = Location("Poznań")

        coEvery { storage.saveToHistory(location) } just Runs
        coEvery { storage.saveLastLocation(location) } just Runs
        coEvery { storage.loadHistory() } returns listOf(location)

        // when
        viewModel.onLocationSelected(location)
        runCurrent()

        // then
        coVerify { storage.saveToHistory(location) }
        coVerify { storage.saveLastLocation(location) }
    }


}