package com.tasks.main.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults.colors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.tasks.main.navigation.NavDestination
import com.tasks.main.ui.theme.Teal

@Composable
fun BottomNavigation(
    navController: NavController,
    destinations: List<NavDestination>,
    onValueChange: (String) -> Unit
) {
    NavigationBar(contentColor = colorScheme.onBackground) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        destinations.forEach {
            NavigationBarItem(
                colors = colors().copy(selectedIconColor = Teal),
                label = { Text(text = it.title) },
                alwaysShowLabel = true,
                selected = navBackStackEntry?.destination?.route == it.route,
                icon = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = it.icon),
                        contentDescription = it.title,
                    )
                },
                onClick = {
                    navController.navigate(it.route) {
                        launchSingleTop = true
                        restoreState = true
                        onValueChange(it.title)

                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) { saveState = true }
                        }
                    }
                }
            )
        }
    }
}