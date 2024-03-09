package com.tasks.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.tasks.main.components.BottomNavigation
import com.tasks.main.navigation.NavDestination
import com.tasks.main.navigation.WeatherNavHost
import com.tasks.main.theme.WeatherAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherMainScreen() {
    WeatherAppTheme {
        val navController = rememberNavController()
        val topBarTitle = remember {
            mutableStateOf(NavDestination.CurrentWeather.title)
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = topBarTitle.value,
                            fontSize = 18.sp,
                            color = colorScheme.onBackground
                        )
                    })
            },
            content = { padding ->
                Box(modifier = Modifier.padding(padding)) {
                    WeatherNavHost(navController = navController)
                }
            },
            bottomBar = {
                BottomNavigation(
                    navController = navController,
                    destinations = listOf(
                        NavDestination.CurrentWeather,
                        NavDestination.Forecasting,
                        NavDestination.Searching
                    ), onValueChange = { topBarTitle.value = it })
            },
        )
    }
}