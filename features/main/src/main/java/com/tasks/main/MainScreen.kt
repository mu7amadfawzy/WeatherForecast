package com.tasks.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.tasks.main.navigation.NavDestination
import com.tasks.main.navigation.WeatherNavHost
import com.tasks.main.ui.BottomNavigation
import com.tasks.main.ui.theme.WeatherAppTheme
import com.tasks.searching.ui.WeatherTopAppBar

@Composable
fun WeatherMainScreen() {
    WeatherAppTheme {
        val navController = rememberNavController()
        var topBarTitle by remember {
            mutableStateOf(NavDestination.CurrentWeather.title)
        }
        var citySearch by remember {
            mutableStateOf<String?>(null)
        }
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                WeatherTopAppBar(
                    title = topBarTitle,
                    onSearchClicked = { citySearch = it },
                )
            },
            content = { padding ->
                Box(modifier = Modifier.padding(padding)) {
                    WeatherNavHost(navController = navController, citySearch = citySearch)
                }
            },
            bottomBar = {
                BottomNavigation(
                    navController = navController,
                    destinations = listOf(
                        NavDestination.CurrentWeather,
                        NavDestination.Forecasting
                    ), onValueChange = { topBarTitle = it })
            },
        )
    }
}