package weather.app.ui.search

import weather.app.models.Location

data class SearchUiState(
    val query: String = "",
    val isValid: Boolean = true,
    val isLoading: Boolean = false,
    val suggestions: List<Location> = emptyList(),
    val history: List<Location> = emptyList(),
    val error: String? = null
)