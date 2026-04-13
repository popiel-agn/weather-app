package weather.app.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import weather.app.repository.WeatherRepository
import weather.app.storage.SearchHistoryStorage

class SearchViewModelFactory(
    private val repository: WeatherRepository,
    private val storage: SearchHistoryStorage
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(repository, storage) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
