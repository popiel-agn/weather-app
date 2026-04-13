package weather.app.utils

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class LocationValidatorTests {

    @Test
    fun `valid locations test`() {
        val validLocations = listOf(
            "Poznań",
            "Kostrzyn nad Odrą",
            "Bielsko-Biała",
            "Henningsvær",
            "Mänttä-Vilppula",
            "Челябинск",
            "São Paulo",
            "مكة",
            "Θεσσαλονίκη",
            "京都市",
            "香港"
        )

        val allValid = validLocations.all { LocationValidator.isValid(it) }

        assertTrue(allValid)
    }

    @Test
    fun `invalid locations test`() {
        val invalidLocations = listOf(
            "Poznań123",
            "@!#$%^&*",
            "[]",
            "//",
            "<>",
            "~"
        )

        val allInvalid = invalidLocations.all { !LocationValidator.isValid(it) }

        assertTrue(allInvalid)
    }
}