package com.hometest.weather.data.remote.dto

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("location") val location: Location,
    @SerializedName("current") val current: CurrentWeather,
)

data class Location(
    @SerializedName("name") val name: String,
    @SerializedName("region") val region: String,
    @SerializedName("country") val country: String,
    @SerializedName("lat") val latitude: Float,
    @SerializedName("lon") val longitude: Float,
)

data class CurrentWeather(
    @SerializedName("temp_c") val temperature: Float,
    @SerializedName("condition") val condition: WeatherCondition,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("uv") val uvIndex: Float,
    @SerializedName("feelslike_c") val feelsLike: Float,
)

data class WeatherCondition(
    @SerializedName("text") val description: String,
    @SerializedName("icon") val iconUrl: String,
    @SerializedName("code") val conditionCode: Int,
)
