package weather.app.ui.forecast

import weather.app.models.Forecast
import weather.app.models.Location

data class ForecastUiState(
    val location: Location? = null,
    val isLoading: Boolean = false,
    val forecast: Forecast? = null,
    val error: String? = null
)