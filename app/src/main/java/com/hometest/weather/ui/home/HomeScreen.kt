package com.hometest.weather.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hometest.weather.data.common.Resource
import com.hometest.weather.data.remote.dto.CurrentWeather
import com.hometest.weather.data.remote.dto.WeatherCondition
import com.hometest.weather.data.remote.dto.WeatherResponse
import com.hometest.weather.ui.common.WeatherCard
import com.hometest.weather.ui.home.data.HomeState
import com.hometest.weather.data.remote.dto.Location
import com.hometest.weather.domain.utils.TemperatureUnit
import com.hometest.weather.ui.home.components.TemperatureToggle
import com.hometest.weather.utils.WeatherIcon

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToDetail: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.homeState.collectAsState()
    val temperatureUnit by viewModel.temperatureUnit.collectAsState()
    var refreshing by remember { mutableStateOf(false) }

    HomeScreen(
        modifier = modifier,
        state = state,
        temperatureUnit = temperatureUnit,
        refreshing = refreshing,
        onRefresh = {
            refreshing = true
            viewModel.refreshCurrentLocationWeather {
                refreshing = false
            }
        },
        onSearch = { location ->
            viewModel.getWeather(location)
        },
        onToggleUnit = {
            viewModel.toggleTemperatureUnit()
        },
        onNavigateToDetail = onNavigateToDetail
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeState,
    temperatureUnit: TemperatureUnit,
    refreshing: Boolean,
    onRefresh: () -> Unit,
    onToggleUnit: () -> Unit,
    onNavigateToDetail: () -> Unit,
    onSearch: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    PullToRefreshBox(
        modifier = modifier.testTag("RefreshTrigger"),
        isRefreshing = refreshing,
        onRefresh = onRefresh
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Search Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Search Bar
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    placeholder = { Text("Search Location") },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        if (searchQuery.isNotBlank()) {
                            onSearch(searchQuery)
                        }
                    }),
                    trailingIcon = {
                        IconButton(onClick = {
                            if (searchQuery.isNotBlank()) {
                                onSearch(searchQuery)
                            }
                        }) {
                            Icon(Icons.Default.Search, contentDescription = "Search Icon")
                        }
                    },
                )
            }
            when {
                state.isLoading -> {
                    CircularProgressIndicator()
                }
                state.error.isNotEmpty() -> {
                    Text(
                        text = state.error,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                state.weatherState is Resource.Success -> {
                    val weather = state.weatherState.data
                    if (weather != null) {
                        TemperatureToggle(
                            currentUnit = temperatureUnit,
                            onToggleUnit = onToggleUnit,
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(8.dp)
                        )

                        val conditionCode = weather.current.condition.conditionCode
                        val weatherIcon = WeatherIcon.fromConditionCode(conditionCode)
                        WeatherCard(
                            location = weather.location.name,
                            temperatureCelsius = weather.current.temperature,
                            temperatureUnit = temperatureUnit,
                            iconRes = weatherIcon.iconRes,
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable { onNavigateToDetail() },
                        )
                    }
                }
                else -> {
                    Text(
                        "No City Selected",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Text(
                        "Please Search For A City",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    var temperatureUnit = remember { mutableStateOf(TemperatureUnit.CELSIUS) }
    HomeScreen(
        modifier = Modifier.padding(top = 32.dp),
        state = HomeState(
            weatherState = Resource.Success(
                WeatherResponse(
                    location = Location(
                        name = "Mumbai",
                        region = "Maharashtra",
                        country = "India",
                        latitude = 19.07f,
                        longitude = 72.87f
                    ),
                    current = CurrentWeather(
                        temperature = 25.0f,
                        condition = WeatherCondition(description = "Sunny", iconUrl = "",   conditionCode = 1136),
                        humidity = 60,
                        uvIndex = 5.0f,
                        feelsLike = 27.0f,
                    )
                )
            )
        ),
        refreshing = false,
        temperatureUnit = TemperatureUnit.CELSIUS,
        onSearch = {},
        onNavigateToDetail = {},
        onRefresh = {},
        onToggleUnit = {
            temperatureUnit.value = if (temperatureUnit.value == TemperatureUnit.CELSIUS) {
                TemperatureUnit.FAHRENHEIT
            } else {
                TemperatureUnit.CELSIUS
            }
        },
    )
}
