package com.tasks.main.navigation

import com.tasks.main.R

sealed class NavDestination(val route: String, val icon: Int, val title: String) {
    data object CurrentWeather :
        NavDestination(Routes.WEATHER, R.drawable.current_weather, "Current Weather")

    data object Forecasting :
        NavDestination(Routes.FORECAST, R.drawable.weather_forecasting, "Forecasting")
}

object Routes {
    const val WEATHER = "weather"
    const val FORECAST = "forecasting"
    const val SEARCH = "searching"
}
