package com.hometest.weather.ui.home.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hometest.weather.domain.utils.TemperatureUnit

@Composable
fun TemperatureToggle(
    currentUnit: TemperatureUnit,
    onToggleUnit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onToggleUnit,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text("Unit: ${currentUnit.name} → ${if (currentUnit == TemperatureUnit.CELSIUS) "F" else "C"}")
    }
}
