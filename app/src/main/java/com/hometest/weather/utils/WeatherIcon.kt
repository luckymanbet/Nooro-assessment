package com.hometest.weather.utils

import androidx.annotation.DrawableRes
import com.hometest.weather.R

sealed class WeatherIcon(val conditionCodes: List<Int>, @DrawableRes val iconRes: Int) {
    object Sunny : WeatherIcon(listOf(1000), R.drawable.ic_sunny_cloud_forecast)
    object PartlyCloudy : WeatherIcon(listOf(1003, 1006), R.drawable.ic_cloudy_forecast_sun)
    object Cloudy : WeatherIcon(listOf(1009), R.drawable.ic_cloud_cloudy_forecast)
    object Rain : WeatherIcon(listOf(1180, 1183, 1186, 1189), R.drawable.ic_cloud_forecast)
    object Thunderstorm : WeatherIcon(listOf(1273, 1276), R.drawable.ic_cloud_forecast_lightning)
    object Snow : WeatherIcon(listOf(1210, 1213, 1216), R.drawable.ic_cloud_night_forecast)
    object Fog : WeatherIcon(listOf(1135, 1147), R.drawable.ic_cloud_night_forecast)
    object Night : WeatherIcon(listOf(1001, 1100), R.drawable.ic_cloud_forecast_moon)

    companion object {
        fun fromConditionCode(conditionCode: Int): WeatherIcon {
            return when (conditionCode) {
                in Sunny.conditionCodes -> Sunny
                in PartlyCloudy.conditionCodes -> PartlyCloudy
                in Cloudy.conditionCodes -> Cloudy
                in Rain.conditionCodes -> Rain
                in Thunderstorm.conditionCodes -> Thunderstorm
                in Snow.conditionCodes -> Snow
                in Fog.conditionCodes -> Fog
                else -> Night
            }
        }
    }
}
