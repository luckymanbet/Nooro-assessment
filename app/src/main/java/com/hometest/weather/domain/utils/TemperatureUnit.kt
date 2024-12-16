package com.hometest.weather.domain.utils

enum class TemperatureUnit(val value: String) {
    CELSIUS("C"),
    FAHRENHEIT("F");

    companion object {
        fun fromValue(value: String): TemperatureUnit {
            return TemperatureUnit.entries.find { it.value == value } ?: CELSIUS
        }
    }
}
