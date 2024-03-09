package com.tasks.main.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tasks.currentweather.ui.WeatherScreen
import com.tasks.forecasting.ForecastScreen
import com.tasks.searching.SearchCityScreen

@Composable
fun WeatherNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavDestination.CurrentWeather.route) {
        composable(NavDestination.CurrentWeather.route) {
            WeatherScreen()
        }

        composable(NavDestination.Forecasting.route) {
            ForecastScreen(navController)
        }

        composable(NavDestination.Searching.route) {
            SearchCityScreen(navController)
        }

    }
}