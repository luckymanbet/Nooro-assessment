package com.hometest.weather.data.repository

import android.content.SharedPreferences
import com.hometest.weather.domain.utils.TemperatureUnit
import javax.inject.Inject

class TemperatureUnitRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    companion object {
        private const val KEY_TEMPERATURE_UNIT = "temperature_unit"
    }

    fun saveTemperatureUnit(unit: TemperatureUnit) {
        sharedPreferences.edit().putString(KEY_TEMPERATURE_UNIT, unit.value).apply()
    }

    fun getTemperatureUnit(): TemperatureUnit {
        val unitValue = sharedPreferences.getString(KEY_TEMPERATURE_UNIT, TemperatureUnit.CELSIUS.value)
        return TemperatureUnit.fromValue(unitValue ?: TemperatureUnit.CELSIUS.value)
    }
}
