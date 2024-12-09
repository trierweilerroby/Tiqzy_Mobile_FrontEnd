package com.example.tiqzy_mobile_frontend.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tiqzy_mobile_frontend.ui.screens.*

@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Tickets.route) { TicketsScreen(navController) }
        composable(Screen.Favorites.route) { FavoritesScreen(navController) }
        composable(Screen.Profile.route) { ProfileScreen(navController) }
    }
}

