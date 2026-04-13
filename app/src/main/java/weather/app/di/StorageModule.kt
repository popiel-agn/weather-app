package weather.app.di

import android.content.Context
import dagger.Module
import dagger.Provides
import weather.app.storage.SearchHistoryStorage
import weather.app.storage.SearchHistoryStorageImpl
import javax.inject.Singleton

@Module
class StorageModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideSearchHistoryStorage(): SearchHistoryStorage {
        return SearchHistoryStorageImpl(context)
    }
}
