package weather.app.ui.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import weather.app.repository.WeatherRepository
import weather.app.utils.Result

private const val FORECAST_DAYS = 7

class ForecastViewModel(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ForecastUiState())
    val uiState: StateFlow<ForecastUiState> = _uiState.asStateFlow()

    fun loadForecast(url: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            when (val result = repository.getForecast(
                url, days = FORECAST_DAYS
            )) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        forecast = result.data,
                        isLoading = false,
                        error = null
                    )
                }

                is Result.Failure -> {
                    _uiState.value = _uiState.value.copy(
                        forecast = null,
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }
}