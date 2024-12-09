package com.example.tiqzy_mobile_frontend.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tiqzy_mobile_frontend.ui.screens.*
import com.example.tiqzy_mobile_frontend.viewmodel.FavoritesViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    favoritesViewModel: FavoritesViewModel, // Inject FavoritesViewModel
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Tickets.route) { TicketsScreen(navController) }
        composable(Screen.Favorites.route) {
            FavoritesScreen(navController, favoritesViewModel)
        }
        composable(Screen.Profile.route) { ProfileScreen(navController) }
    }
}
