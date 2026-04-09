package weather.app.data.mappers

import weather.app.data.remote.dto.LocationSearchDTO
import weather.app.models.Location

fun LocationSearchDTO.toDomain(): Location {
    return Location(
        name = name,
        region = region.ifEmpty { null },
        country = country,
        lat = lat,
        lon = lon
    )
}
