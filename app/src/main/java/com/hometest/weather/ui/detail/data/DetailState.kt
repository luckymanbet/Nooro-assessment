package com.hometest.weather.ui.detail.data

import com.hometest.weather.data.remote.dto.CurrentWeather
import com.hometest.weather.domain.utils.TemperatureUnit

data class DetailState(
    val location: String = "",
    val temperature: Float = 0f,
    val conditionDescription: String = "",
    val iconUrl: String = "",
    val humidity: Int = 0,
    val uvIndex: Float = 0f,
    val feelsLikeTemperature: Float = 0f,
    val temperatureUnit: TemperatureUnit,
    val conditionCode: Int = 0,
) {
    companion object {
        fun fromCurrentWeather(location: String, weather: CurrentWeather, temperatureUnit: TemperatureUnit): DetailState {
            return DetailState(
                location = location,
                temperature = weather.temperature,
                conditionDescription = weather.condition.description,
                iconUrl = weather.condition.iconUrl,
                humidity = weather.humidity,
                uvIndex = weather.uvIndex,
                feelsLikeTemperature = weather.feelsLike,
                temperatureUnit = temperatureUnit,
                conditionCode = weather.condition.conditionCode
            )
        }
    }
}
