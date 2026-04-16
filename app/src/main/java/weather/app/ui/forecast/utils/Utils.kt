package weather.app.ui.forecast.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import weather.app.R

// https://www.weatherapi.com/docs/weather_conditions.xml
fun mapWeatherCodeToIcon(code: Int): Int = when (code) {
    1000 -> 113
    1003 -> 116
    1006 -> 119
    1009 -> 122
    1030 -> 143
    1063 -> 176
    1066 -> 179
    1069 -> 182
    1072 -> 185
    1087 -> 200
    1114 -> 227
    1117 -> 230
    1135 -> 248
    1147 -> 260
    1150 -> 263
    1153 -> 266
    1168 -> 281
    1171 -> 284
    1180 -> 293
    1183 -> 296
    1186 -> 299
    1189 -> 302
    1192 -> 305
    1195 -> 308
    1198 -> 311
    1201 -> 314
    1204 -> 317
    1207 -> 320
    1210 -> 323
    1213 -> 326
    1216 -> 329
    1219 -> 332
    1222 -> 335
    1225 -> 338
    1237 -> 350
    1240 -> 353
    1243 -> 356
    1246 -> 359
    1249 -> 362
    1252 -> 365
    1255 -> 368
    1258 -> 371
    1261 -> 374
    1264 -> 377
    1273 -> 386
    1276 -> 389
    1279 -> 392
    1282 -> 395
    // fallback Cloudy
    else -> 119
}

fun mapConditionTextToStringRes(text: String): Int = when (text.lowercase()) {
    "sunny" -> R.string.cond_sunny
    "clear" -> R.string.cond_sunny
    "partly cloudy" -> R.string.cond_partly_cloudy
    "cloudy" -> R.string.cond_cloudy
    "overcast" -> R.string.cond_overcast
    "mist" -> R.string.cond_mist
    "patchy rain possible", "patchy rain" -> R.string.cond_patchy_rain
    "patchy snow possible", "patchy snow" -> R.string.cond_patchy_snow
    "patchy sleet possible", "patchy sleet" -> R.string.cond_patchy_sleet
    "patchy freezing drizzle possible" -> R.string.cond_patchy_freezing_drizzle
    "thundery outbreaks possible", "thundery" -> R.string.cond_thundery
    "blowing snow" -> R.string.cond_blowing_snow
    "blizzard" -> R.string.cond_blizzard
    "fog" -> R.string.cond_fog
    "freezing fog" -> R.string.cond_freezing_fog
    "patchy light drizzle" -> R.string.cond_patchy_light_drizzle
    "light drizzle" -> R.string.cond_light_drizzle
    "freezing drizzle" -> R.string.cond_freezing_drizzle
    "heavy freezing drizzle" -> R.string.cond_heavy_freezing_drizzle
    "patchy light rain" -> R.string.cond_patchy_light_rain
    "light rain" -> R.string.cond_light_rain
    "moderate rain at times", "moderate rain" -> R.string.cond_moderate_rain
    "heavy rain at times", "heavy rain" -> R.string.cond_heavy_rain
    "light freezing rain" -> R.string.cond_light_freezing_rain
    "moderate or heavy freezing rain", "moderate freezing rain" -> R.string.cond_moderate_freezing_rain
    "light sleet" -> R.string.cond_light_sleet
    "moderate or heavy sleet", "moderate sleet" -> R.string.cond_moderate_sleet
    "patchy light snow" -> R.string.cond_patchy_light_snow
    "light snow" -> R.string.cond_light_snow
    "patchy moderate snow" -> R.string.cond_patchy_moderate_snow
    "moderate snow" -> R.string.cond_moderate_snow
    "patchy heavy snow" -> R.string.cond_patchy_heavy_snow
    "heavy snow" -> R.string.cond_heavy_snow
    "ice pellets" -> R.string.cond_ice_pellets
    "light rain shower" -> R.string.cond_light_rain_shower
    "moderate or heavy rain shower" -> R.string.cond_moderate_rain_shower
    "torrential rain shower" -> R.string.cond_torrential_rain_shower
    "light sleet showers" -> R.string.cond_light_sleet_showers
    "moderate or heavy sleet showers" -> R.string.cond_moderate_sleet_showers
    "light snow showers" -> R.string.cond_light_snow_showers
    "moderate or heavy snow showers" -> R.string.cond_moderate_snow_showers
    "light showers of ice pellets" -> R.string.cond_light_ice_pellet_showers
    "moderate or heavy showers of ice pellets" -> R.string.cond_heavy_ice_pellet_showers
    "patchy light rain with thunder" -> R.string.cond_patchy_rain_thunder
    "moderate or heavy rain with thunder" -> R.string.cond_moderate_rain_thunder
    "patchy light snow with thunder" -> R.string.cond_patchy_snow_thunder
    "moderate or heavy snow with thunder" -> R.string.cond_moderate_snow_thunder
    else -> R.string.cond_cloudy
}

@Composable
fun tempColor(temp: Double): Color =
    when {
        temp < 10 -> Color(0xFF277DE3)
        temp in 10.0..20.0 -> Color.Black
        else -> Color(0xFFEF2D2D)
    }
