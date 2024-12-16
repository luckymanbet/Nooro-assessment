package com.hometest.weather.ui.home.data

import com.hometest.weather.data.common.Resource
import com.hometest.weather.data.remote.dto.WeatherResponse

data class HomeState(
    val isLoading: Boolean = false,
    val weatherState: Resource<WeatherResponse> = Resource.Loading(),
    val error: String = "",
)
