package com.example.tiqzy_mobile_frontend.ui.navigation

import com.example.tiqzy_mobile_frontend.viewmodel.FavoritesViewModel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tiqzy_mobile_frontend.ui.screens.*


@Composable
fun AppNavHost(
    navController: NavHostController,
    favoritesViewModel: FavoritesViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController, favoritesViewModel = favoritesViewModel)
        }
        composable(
            route = "eventList/{location}/{date}",
            arguments = listOf(
                navArgument("location") { type = NavType.StringType },
                navArgument("date") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val location = backStackEntry.arguments?.getString("location") ?: ""
            val date = backStackEntry.arguments?.getString("date") ?: ""

            EventListScreen(
                navController = navController,
                location = location,
                date = date,
                favoritesViewModel = favoritesViewModel
            )
        }
        composable(Screen.Tickets.route) { TicketsScreen(navController = navController) }
        composable(Screen.Favorites.route) { FavoritesScreen(navController = navController, favoritesViewModel = favoritesViewModel) }
        composable(Screen.Profile.route) { ProfileScreen(navController = navController) }
    }
}


