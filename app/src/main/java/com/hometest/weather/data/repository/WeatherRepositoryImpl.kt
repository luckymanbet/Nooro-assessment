package com.hometest.weather.data.repository

import com.hometest.weather.data.common.Constants
import com.hometest.weather.data.remote.WeatherApiService
import com.hometest.weather.data.remote.dto.WeatherResponse
import com.hometest.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val apiService: WeatherApiService) :
    WeatherRepository {

    @Override
    override suspend fun getWeather(location: String): WeatherResponse {
        return apiService.getCurrentWeather(Constants.API_KEY, location)
    }
}
