package weather.app.di

import dagger.Binds
import dagger.Module
import weather.app.repository.WeatherRepository
import weather.app.repository.WeatherRepositoryImpl

@Module
interface RepositoryModule {

    @Binds
    fun bindWeatherRepository(
        impl: WeatherRepositoryImpl
    ): WeatherRepository
}
