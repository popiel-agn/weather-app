package weather.app.ui.forecast.utils

import weather.app.R
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

@Composable
fun WeatherIcon(code: Int, isDay: Boolean, modifier: Modifier = Modifier) {
    val iconUrl = buildWeatherIconUrl(code, isDay)

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(iconUrl)
            .crossfade(true)
            .build(),
        contentDescription = null,
        modifier = modifier.size(40.dp),
        placeholder = painterResource(R.drawable.placeholder),
        error = painterResource(R.drawable.placeholder)
    )
}


fun buildWeatherIconUrl(code: Int, isDay: Boolean): String {
    val iconNumber = mapWeatherCodeToIcon(code)
    val dayOrNight = if (isDay) "day" else "night"
    return "https://cdn.weatherapi.com/weather/64x64/$dayOrNight/$iconNumber.png"
}
