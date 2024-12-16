package com.hometest.weather.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hometest.weather.ui.detail.data.DetailState

@Composable
fun WeatherDetails(
    modifier: Modifier = Modifier, state: DetailState
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            WeatherDetailItem(label = "Humidity", value = "${state.humidity}%")
            WeatherDetailItem(label = "UV Index", value = "${state.uvIndex.toInt()}")
            WeatherDetailItem(
                label = "Feels Like",
                value = "${state.feelsLikeTemperature.toInt()}°"
            )
        }
    }
}