package weather.app

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import weather.app.nav.AppNavHost
import weather.app.ui.forecast.ForecastViewModelFactory
import weather.app.ui.search.SearchViewModelFactory

@Composable
fun WeatherAppRoot(
    searchFactory: SearchViewModelFactory,
    forecastFactory: ForecastViewModelFactory
) {
    MaterialTheme {
        AppNavHost(
            searchFactory = searchFactory,
            forecastFactory = forecastFactory
        )
    }
}
