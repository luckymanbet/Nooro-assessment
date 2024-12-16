package com.hometest.weather.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hometest.weather.domain.utils.TemperatureUnit
import com.hometest.weather.ui.common.WeatherDetails
import com.hometest.weather.ui.detail.data.DetailState
import com.hometest.weather.utils.WeatherIcon

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    state: DetailState?
) {

    DetailScreen(
        modifier = modifier,
        detailState = state,
    )
}

@Composable
fun DetailScreen(
    detailState: DetailState?,
    modifier: Modifier = Modifier
) {
    detailState?.let { state ->
        val temperature = if (detailState.temperatureUnit == TemperatureUnit.FAHRENHEIT) {
            detailState.temperature * 9 / 5 + 32
        } else {
            detailState.temperature
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val conditionCode = detailState.conditionCode
            val weatherIcon = WeatherIcon.fromConditionCode(conditionCode)

            Icon(
                painter = painterResource(id = weatherIcon.iconRes),
                contentDescription = state.conditionDescription,
                tint = Color.Unspecified,
                modifier = Modifier.size(120.dp)
            )

            // Location and Temperature
            Text(
                text = state.location,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "${temperature.toInt()}°",
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )

            WeatherDetails(modifier = Modifier, state = state)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailScreenPreview() {
    val previewState = DetailState(
        location = "Pune",
        temperature = 45f,
        conditionDescription = "Sunny",
        iconUrl = "",
        humidity = 20,
        uvIndex = 4f,
        feelsLikeTemperature = 38f,
        temperatureUnit = TemperatureUnit.CELSIUS
    )

    DetailScreen(detailState = previewState)
}
