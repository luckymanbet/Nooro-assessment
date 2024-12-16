package com.hometest.weather.domain.usecases

import com.hometest.weather.data.common.Resource
import com.hometest.weather.data.remote.dto.WeatherResponse
import com.hometest.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    operator fun invoke(location: String): Flow<Resource<WeatherResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = repository.getWeather(location)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }
}
