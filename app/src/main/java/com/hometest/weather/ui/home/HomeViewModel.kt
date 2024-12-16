package com.hometest.weather.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hometest.weather.data.common.Resource
import com.hometest.weather.data.repository.TemperatureUnitRepository
import com.hometest.weather.domain.usecases.GetWeatherUseCase
import com.hometest.weather.domain.utils.TemperatureUnit
import com.hometest.weather.ui.detail.data.DetailState
import com.hometest.weather.ui.home.data.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val temperatureUnitRepository: TemperatureUnitRepository,
    private val sharedPreferences: android.content.SharedPreferences,
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState: StateFlow<HomeState> = _homeState

    private val _detailState = MutableStateFlow<DetailState?>(null)
    val detailState: StateFlow<DetailState?> = _detailState

    private val _temperatureUnit = MutableStateFlow(temperatureUnitRepository.getTemperatureUnit())
    val temperatureUnit: StateFlow<TemperatureUnit> = _temperatureUnit

    private var currentLocation = ""

    init {
        observeTemperatureUnit()
        loadLastSearchAndFetchWeather()
    }

    private fun loadLastSearchAndFetchWeather() {
        viewModelScope.launch {
            val lastSearch = sharedPreferences.getString("last_search", null)
            if (!lastSearch.isNullOrBlank()) {
                currentLocation = lastSearch
                getWeather(currentLocation)
            }
        }
    }

    /**
     * Refreshes the weather for the current location.
     * Calls `onComplete` when the refresh operation finishes.
     */
    fun refreshCurrentLocationWeather(onComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                _homeState.value = _homeState.value.copy(isLoading = true)
                val location = currentLocation
                getWeatherUseCase(location).collect { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            _homeState.value = _homeState.value.copy(isLoading = true)
                        }
                        is Resource.Success -> {
                            val weatherResponse = resource.data
                            if (weatherResponse != null) {
                                _homeState.value = _homeState.value.copy(
                                    isLoading = false,
                                    weatherState = Resource.Success(weatherResponse)
                                )
                                _detailState.value = DetailState.fromCurrentWeather(
                                    location = weatherResponse.location.name,
                                    weather = weatherResponse.current,
                                    temperatureUnit = temperatureUnit.value
                                )
                            }
                        }
                        is Resource.Error -> {
                            _homeState.value = _homeState.value.copy(
                                isLoading = false,
                                error = resource.message ?: "An error occurred while refreshing"
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _homeState.value = _homeState.value.copy(
                    isLoading = false,
                    error = "An unexpected error occurred: ${e.message}"
                )
            } finally {
                onComplete()
            }
        }
    }

    fun getWeather(location: String) {
        currentLocation = location
        saveLastSearch(location)
        _homeState.value = _homeState.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                getWeatherUseCase(location).collect { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            _homeState.value = _homeState.value.copy(isLoading = true)
                        }
                        is Resource.Success -> {
                            val weatherResponse = resource.data
                            if (weatherResponse != null) {
                                _homeState.value = _homeState.value.copy(
                                    isLoading = false,
                                    weatherState = Resource.Success(weatherResponse)
                                )
                                _detailState.value = DetailState.fromCurrentWeather(
                                    location = weatherResponse.location.name,
                                    weather = weatherResponse.current,
                                    temperatureUnit = temperatureUnit.value
                                )
                            }
                        }
                        is Resource.Error -> {
                            _homeState.value = _homeState.value.copy(
                                isLoading = false,
                                error = resource.message ?: "An unexpected error occurred"
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _homeState.value = _homeState.value.copy(
                    isLoading = false,
                    error = "An unexpected error occurred: ${e.message}"
                )
            }
        }
    }

    fun toggleTemperatureUnit() {
        val newUnit = if (_temperatureUnit.value == TemperatureUnit.CELSIUS) {
            TemperatureUnit.FAHRENHEIT
        } else {
            TemperatureUnit.CELSIUS
        }
        temperatureUnitRepository.saveTemperatureUnit(newUnit)
        _temperatureUnit.value = newUnit
    }

    private fun observeTemperatureUnit() {
        viewModelScope.launch {
            _temperatureUnit.collect { unit ->
                _detailState.value?.let { currentDetailState ->
                    _detailState.value = currentDetailState.copy(temperatureUnit = unit)
                }
            }
        }
    }

    private fun saveLastSearch(location: String) {
        sharedPreferences.edit()
            .putString("last_search", location)
            .apply()
    }
}
