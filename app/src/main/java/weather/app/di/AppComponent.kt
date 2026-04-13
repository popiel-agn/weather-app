package weather.app.di

import dagger.Component
import weather.app.storage.SearchHistoryStorage
import javax.inject.Singleton


@Singleton
@Component(modules = [StorageModule::class])
interface AppComponent {

    fun searchHistoryStorage(): SearchHistoryStorage

    @Component.Factory
    interface Factory {
        fun create(storageModule: StorageModule): AppComponent
    }
}
