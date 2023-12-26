package org.fitness.myfitnesstrainer.ui.composables.MyFitnessInputFields

fun filterAfterDecimalChars(maxChars: Int, stringToFilter: String): String {
    val newString = stringToFilter.substringAfterLast(".")

    if (newString == stringToFilter) {
        return stringToFilter
    }

    return if (newString.length <= maxChars)
        stringToFilter
    else stringToFilter.dropLast(1)
}

fun filterMaxChars(maxChars: Int, stringToFilter: String): String {
    return if (stringToFilter.length <= maxChars)
        stringToFilter
    else stringToFilter.dropLast(1)
}