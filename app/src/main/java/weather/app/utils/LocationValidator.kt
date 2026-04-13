package weather.app.utils

object LocationValidator {

    private val regex = Regex("^[\\p{L} .'-]{2,}$")


    fun isValid(input: String): Boolean {
        return regex.matches(input.trim())
    }
}