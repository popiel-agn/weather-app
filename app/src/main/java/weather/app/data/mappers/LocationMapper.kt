package weather.app.data.mappers

import weather.app.data.remote.dto.LocationForecastDTO
import weather.app.data.remote.dto.LocationSearchDTO
import weather.app.models.location.ForecastLocation
import weather.app.models.location.Location
import weather.app.models.location.StoredLocation
import weather.app.models.location.SearchLocation

// DTOs
fun LocationSearchDTO.toStored() = SearchLocation(
    id = id,
    name = name,
    region = region,
    country = country,
    lat = lat,
    lon = lon,
    url = url,

)

fun LocationForecastDTO.toStored() = ForecastLocation(
    name = name,
    region = region,
    country = country,
    lat = lat,
    lon = lon,
    timezone = tz_id,
    localTime = localtime
)

// Search, Stored, Domain
fun SearchLocation.toDomain() = Location(
    id = id,
    name = name,
    region = region,
    country = country,
    lat = lat,
    lon = lon,
    url = url
)

fun Location.toSearch() = SearchLocation(
    id = id,
    name = name,
    region = region,
    country = country,
    lat = lat,
    lon = lon,
    url = url
)

fun Location.toStored() = StoredLocation(
    id = id,
    name = name,
    region = region,
    country = country,
    lat = lat,
    lon = lon,
    url = url
)

fun StoredLocation.toDomain() = Location(
    id = id,
    name = name,
    region = region,
    country = country,
    lat = lat,
    lon = lon,
    url = url
)