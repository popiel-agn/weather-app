package weather.app.ui.forecast

import weather.app.models.Forecast

data class ForecastUiState(
    val isLoading: Boolean = false,
    val forecast: Forecast? = null,
    val error: String? = null
)