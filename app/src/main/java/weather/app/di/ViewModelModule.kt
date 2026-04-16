package weather.app.di

import dagger.Module
import dagger.Provides
import weather.app.repository.WeatherRepository
import weather.app.storage.SearchHistoryStorage
import weather.app.ui.forecast.ForecastViewModelFactory
import weather.app.ui.search.SearchViewModelFactory

@Module
class ViewModelModule {

    @Provides
    fun provideSearchViewModelFactory(
        repo: WeatherRepository,
        storage: SearchHistoryStorage
    ): SearchViewModelFactory =
        SearchViewModelFactory(repo, storage)

    @Provides
    fun provideForecastViewModelFactory(
        repo: WeatherRepository
    ): ForecastViewModelFactory =
        ForecastViewModelFactory(repo)
}
