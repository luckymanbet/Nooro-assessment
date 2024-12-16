package com.hometest.weather.ui.navigation

import kotlinx.serialization.Serializable

interface Routes {
    @Serializable
    data object Home : Routes

    @Serializable
    data object Detail : Routes
}
