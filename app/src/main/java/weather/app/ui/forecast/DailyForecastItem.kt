package weather.app.ui.forecast

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import weather.app.models.Forecast
import weather.app.ui.forecast.utils.WeatherIcon

@Composable
fun DailyForecastItem(day: Forecast.ForecastDay) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = day.date,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge
        )

        WeatherIcon(
            code = day.condition.code,
            isDay = true
        )

        Text(
            text = "${day.avgTempC.toInt()}°C",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

