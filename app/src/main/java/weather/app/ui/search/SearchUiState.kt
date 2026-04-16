package weather.app.ui.search

import weather.app.models.location.Location
import weather.app.models.location.SearchLocation

data class SearchUiState(
    val query: String = "",
    val isValid: Boolean = true,
    val isLoading: Boolean = false,
    val suggestions: List<SearchLocation> = emptyList(),
    val history: List<Location> = emptyList(),
    val lastLocation: Location? = null,
    val error: String? = null
)