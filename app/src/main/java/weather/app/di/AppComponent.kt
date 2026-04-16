package weather.app.di

import dagger.Component
import weather.app.MainActivity
import weather.app.storage.SearchHistoryStorage
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        StorageModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    fun searchHistoryStorage(): SearchHistoryStorage

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(storageModule: StorageModule): AppComponent
    }
}
