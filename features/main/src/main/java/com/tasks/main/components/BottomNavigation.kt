package com.tasks.main.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tasks.main.navigation.NavDestination
import com.tasks.main.theme.WeatherAppTheme

@Composable
fun BottomNavigation(navController: NavController, onValueChange: (String) -> Unit) {
    val items = listOf(
        NavDestination.CurrentWeather,
        NavDestination.Forecasting,
        NavDestination.Searching
    )
    NavigationBar(contentColor = MaterialTheme.colorScheme.onBackground) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
//                colors = NavigationBarItemColors(selectedIconColor = Teal),
                label = { Text(text = item.title) },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        modifier = Modifier
                            .height(20.dp)
                            .width(20.dp),
                        contentDescription = item.title
                    )
                },
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }

                        launchSingleTop = true
                        restoreState = true
                        onValueChange(item.title)
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GreetingPreview() {
    WeatherAppTheme {
        BottomNavigation(rememberNavController()) {
            
        }
    }
}