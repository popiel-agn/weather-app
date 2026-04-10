package weather.app.data.mappers

import weather.app.data.remote.dto.LocationDTO
import weather.app.models.Location

fun LocationDTO.toDomain(): Location {
    return Location(
        name = name,
        region = region.ifEmpty { null },
        country = country,
        lat = lat,
        lon = lon,
        localTime = localTime?.ifEmpty { null }
    )
}
