package com.example.tiqzy_mobile_frontend.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.tiqzy_mobile_frontend.R
import com.example.tiqzy_mobile_frontend.ui.navigation.Screen

@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(
        Screen.Home,
        Screen.Tickets,
        Screen.Favorites,
        Screen.Profile
    )

    NavigationBar(
        containerColor = Color(0xFF1A2B48) // Set the background color to #1A2B48
    ) {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route

        items.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = when (screen) {
                                Screen.Home -> R.drawable.home_icon
                                Screen.Tickets -> R.drawable.ticket
                                Screen.Favorites -> R.drawable.heart
                                Screen.Profile -> R.drawable.profile
                                else -> R.drawable.help
                            }
                        ),
                        contentDescription = screen.title,
                        modifier = Modifier.size(26.dp),
                        tint = Color.White // Set icon color to white
                    )
                },
                label = {
                    Text(
                        text = screen.title,
                        color = Color.White // Set label text color to white
                    )
                },
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo("home") {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.White.copy(alpha = 0.6f),
                    selectedTextColor = Color.White,
                    unselectedTextColor = Color.White.copy(alpha = 0.6f),
                    indicatorColor = Color(0xFF1A2B48) // Match background for selected state
                )
            )
        }
    }
}
