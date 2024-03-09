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
                TopBar(topBarTitle.value)
            },
            bottomBar = {
                BottomNavigation(navController) {
                    topBarTitle.value = it
                }
            },
            content = { padding ->
                Box(modifier = Modifier.padding(padding)) {
                    WeatherNavHost(navController = navController)
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(title: String) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontSize = 18.sp,
                color = colorScheme.onBackground
            )
        })
}