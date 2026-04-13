package weather.app.ui.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import weather.app.repository.WeatherRepository

class ForecastViewModelFactory(
    private val repository: WeatherRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ForecastViewModel::class.java)) {
            return ForecastViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
