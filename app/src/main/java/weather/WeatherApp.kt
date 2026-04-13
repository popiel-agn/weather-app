package weather

import android.app.Application
import weather.app.di.AppComponent
import weather.app.di.StorageModule
import weather.app.di.DaggerAppComponent

class WeatherApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .factory()
            .create(StorageModule(this))
    }
}
