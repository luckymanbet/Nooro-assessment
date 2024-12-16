package com.hometest.weather.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.Icon
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.hometest.weather.R
import com.hometest.weather.domain.utils.TemperatureUnit

@Composable
fun WeatherCard(
    location: String,
    temperatureCelsius: Float,
    temperatureUnit: TemperatureUnit,
    iconRes: Int,
    modifier: Modifier = Modifier
) {
    val temperature = if (temperatureUnit == TemperatureUnit.FAHRENHEIT) {
        temperatureCelsius * 9 / 5 + 32
    } else {
        temperatureCelsius
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = location,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${temperature.toInt()}°",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 32.sp
                )
            }

            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(48.dp)
                    .weight(0.3f)
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun WeatherCardPreview() {
    var temperatureUnit = remember { mutableStateOf(TemperatureUnit.CELSIUS) }

    WeatherCard(
        location = "Mumbai",
        iconRes = R.drawable.ic_cloud_forecast,
        modifier = Modifier
            .padding(8.dp),
        temperatureUnit = TemperatureUnit.CELSIUS,
        temperatureCelsius = 20f,
    )
}
