package weather.app.data.mappers

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import weather.app.data.remote.dto.LocationSearchDTO

class LocationMapperTests {
    
    @Test
    fun `LocationSearchDTO to Location mapping`() {
        val locationSearchDTO = LocationSearchDTO(
            id = 1976891,
            name = "Poznan",
            region = "",
            country = "Country",
            lat = 40.7128,
            lon = -74.006
        )

        val location = locationSearchDTO.toDomain()

        assertEquals(locationSearchDTO.name, location.name)
        assertEquals(locationSearchDTO.country, location.country)
        assertEquals(null, location.region)
    }
}