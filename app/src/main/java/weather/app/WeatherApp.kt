package weather.app

import android.app.Application
import weather.app.di.AppComponent
import weather.app.di.DaggerAppComponent
import weather.app.di.StorageModule

class WeatherApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .factory()
            .create(StorageModule(this))
    }
}