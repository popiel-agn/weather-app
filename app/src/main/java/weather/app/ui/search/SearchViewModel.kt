package weather.app.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import weather.app.data.mappers.toDomain
import weather.app.models.location.SearchLocation
import weather.app.repository.WeatherRepository
import weather.app.utils.Result
import weather.app.storage.SearchHistoryStorage

class SearchViewModel(
    private val repository: WeatherRepository,
    private val historyStorage: SearchHistoryStorage
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    init {
        loadLastLocation()
        loadHistory()
    }

    fun onQueryChange(query: String) {
        _uiState.update { it.copy(query = query) }

        if (query.isBlank()) {
            _uiState.update {
                it.copy(
                    suggestions = emptyList(),
                    isLoading = false
                )
            }
            loadHistory()
            return
        }

        debounceSearch(query)
    }

    private fun debounceSearch(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300)
            searchLocation(query)
        }
    }

    private suspend fun searchLocation(query: String) {
        _uiState.update { it.copy(isLoading = true, error = null) }

        when (val result = repository.searchLocation(query)) {
            is Result.Success -> {
                _uiState.update {
                    it.copy(
                        suggestions = result.data,
                        isLoading = false,
                        history = emptyList()
                    )
                }
            }

            is Result.Failure -> {
                _uiState.update {
                    it.copy(
                        suggestions = emptyList(),
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }

    private fun loadHistory() {
        viewModelScope.launch {
            val history = historyStorage.loadHistory()
            _uiState.update {
                it.copy(
                    history = history,
                    isLoading = false
                )
            }
        }
    }

    fun onLocationSelected(searchLocation: SearchLocation) {
        val domain = searchLocation.toDomain()

        viewModelScope.launch {
            historyStorage.saveToHistory(domain)
            historyStorage.saveLastLocation(domain)
            loadHistory()
            loadLastLocation()
        }
    }

    private fun loadLastLocation() {
        viewModelScope.launch {
            val last = historyStorage.loadLastLocation()
            _uiState.update {
                it.copy(lastLocation = last)
            }
        }
    }
}
