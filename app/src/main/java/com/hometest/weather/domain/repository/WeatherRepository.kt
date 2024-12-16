package com.hometest.weather.domain.repository

import com.hometest.weather.data.remote.dto.WeatherResponse

interface WeatherRepository {
    suspend fun getWeather(location: String): WeatherResponse
}
