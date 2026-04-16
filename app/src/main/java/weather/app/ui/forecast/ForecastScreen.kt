package weather.app.ui.forecast

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import weather.app.R
import weather.app.ui.forecast.utils.WeatherIcon
import weather.app.ui.forecast.utils.mapConditionTextToStringRes
import weather.app.ui.forecast.utils.tempColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastScreen(
    viewModel: ForecastViewModel,
    locationUrl: String
) {
    Log.d("FORECAST", "Loading forecast for: $locationUrl")

    LaunchedEffect(locationUrl) {
        viewModel.loadForecast(locationUrl)
    }

    val state by viewModel.uiState.collectAsState()

    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val forecast = state.forecast ?: return
    val current = forecast.current

    Scaffold()
    { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = forecast.searchLocation.name,
                style = MaterialTheme.typography.displayMedium
            )

            Text(
                text = forecast.searchLocation.localTime
            )

            Spacer(Modifier.height(16.dp))

            // current temp
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                WeatherIcon(
                    code = current.condition.code,
                    isDay = current.isDay,
                    modifier = Modifier.size(60.dp).padding(10.dp)
                )

                Text(
                    text = "${current.temperatureC.toInt()}°C",
                    style = MaterialTheme.typography.displayLarge,
                    color = tempColor(current.temperatureC),
                    modifier = Modifier.padding(top = 32.dp, bottom = 16.dp)
                )
            }
            Text(
                text = stringResource(R.string.feels_like, current.feelsLikeC.toInt()),
                style = MaterialTheme.typography.bodyLarge
            )

            val condRes = mapConditionTextToStringRes(current.condition.text)

            Text(
                text = stringResource(condRes),
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(Modifier.height(32.dp))

            // forecast
            Text(
                stringResource(R.string.forecast),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 8.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(forecast.days) { day ->
                    DailyForecastItem(day)
                }
            }
        }
    }
}
