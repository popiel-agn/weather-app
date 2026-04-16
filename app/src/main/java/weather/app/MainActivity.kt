package weather.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import weather.app.ui.forecast.ForecastViewModelFactory
import weather.app.ui.search.SearchViewModelFactory
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var searchFactory: SearchViewModelFactory

    @Inject
    lateinit var forecastFactory: ForecastViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as WeatherApp).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppRoot(
                searchFactory = searchFactory,
                forecastFactory = forecastFactory
            )
        }
    }
}
