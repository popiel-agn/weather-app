package weather.app.data.mappers

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import weather.app.data.remote.dto.LocationDTO

class LocationMapperTests {
    
    @Test
    fun `LocationSearchDTO to Location mapping handles empty strings`() {
        val locationDTO = LocationDTO(
            name = "Poznan",
            region = "",
            country = "Country",
            lat = 40.7128,
            lon = -74.006,
            localTime = ""
        )

        val location = locationDTO.toDomain()

        assertEquals(locationDTO.name, location.name)
        assertEquals(locationDTO.country, location.country)
        assertEquals(null, location.region)
        assertEquals(null, location.localTime)
    }

    @Test
    fun `LocationSearchDTO to Location mapping handles null localtime`() {
        val locationDTO = LocationDTO(
            name = "Poznan",
            region = "",
            country = "Country",
            lat = 40.7128,
            lon = -74.006,
            localTime = null
        )

        val location = locationDTO.toDomain()

        assertEquals(locationDTO.name, location.name)
        assertEquals(locationDTO.country, location.country)
        assertEquals(null, location.region)
        assertEquals(null, location.localTime)
    }
}