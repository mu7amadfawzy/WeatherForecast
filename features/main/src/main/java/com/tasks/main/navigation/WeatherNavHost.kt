package com.tasks.main.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tasks.currentweather.ui.WeatherScreen
import com.tasks.forecasting.ForecastScreen

@Composable
fun WeatherNavHost(navController: NavHostController, citySearch: String) {
    NavHost(navController = navController, startDestination = NavDestination.CurrentWeather.route) {
        composable(NavDestination.CurrentWeather.route) {
            WeatherScreen(citySearch)
        }

        composable(NavDestination.Forecasting.route) {
            ForecastScreen(city = citySearch)
        }
    }
}