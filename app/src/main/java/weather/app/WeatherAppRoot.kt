package weather.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import weather.app.nav.AppNavHost
import weather.app.ui.forecast.ForecastViewModelFactory
import weather.app.ui.search.SearchViewModelFactory

@Composable
fun WeatherAppRoot(
    searchFactory: SearchViewModelFactory,
    forecastFactory: ForecastViewModelFactory,
    darkTheme: Boolean = isSystemInDarkTheme(),
) {
    MaterialTheme(
        colorScheme = if (darkTheme) darkColorScheme() else lightColorScheme(),
    ) {
        AppNavHost(
            searchFactory = searchFactory,
            forecastFactory = forecastFactory
        )
    }
}
