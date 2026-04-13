package weather.app.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import weather.app.models.Location
import weather.app.repository.WeatherRepository
import weather.app.utils.Result
import weather.app.utils.LocationValidator
import weather.app.storage.SearchHistoryStorage

class SearchViewModel(
    private val repository: WeatherRepository,
    private val historyStorage: SearchHistoryStorage
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    init {
        loadHistory()
    }

    fun onQueryChange(query: String) {
        _uiState.value = _uiState.value.copy(query = query)

        validate(query)

        if (query.isBlank()) {
            loadHistory()
            return
        }

        debounceSearch(query)
    }

    private fun validate(query: String) {
        val isValid = LocationValidator.isValid(query)
        _uiState.value = _uiState.value.copy(isValid = isValid)
    }

    private fun debounceSearch(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300)
            searchLocation(query)
        }
    }

    private fun searchLocation(query: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            when (val result = repository.searchLocation(query)) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        suggestions = result.data,
                        isLoading = false,
                        error = null
                    )
                }
                is Result.Failure -> {
                    _uiState.value = _uiState.value.copy(
                        suggestions = emptyList(),
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }

    fun onLocationSelected(location: Location) {
        saveToHistory(location)
        saveLastLocation(location)
    }

    private fun loadHistory() {
        viewModelScope.launch {
            val history = historyStorage.loadHistory()
            _uiState.value = _uiState.value.copy(history = history)
        }
    }

    private fun saveToHistory(location: Location) {
        viewModelScope.launch {
            historyStorage.saveToHistory(location)
            loadHistory()
        }
    }

    private fun saveLastLocation(location: Location) {
        viewModelScope.launch {
            historyStorage.saveLastLocation(location)
        }
    }
}
