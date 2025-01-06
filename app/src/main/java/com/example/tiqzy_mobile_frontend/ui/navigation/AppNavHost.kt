package com.example.tiqzy_mobile_frontend.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tiqzy_mobile_frontend.ui.screens.*
import com.example.tiqzy_mobile_frontend.viewmodel.FavoritesViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    dataStore: DataStore<Preferences>,
    favoritesViewModel: FavoritesViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "onboarding",
        modifier = modifier
    ) {
        composable("onboarding") {
            OnboardingScreen(navController = navController)
        }
        composable("onboardingName") {
            OnboardingNameScreen(navController = navController, dataStore = dataStore)
        }
        composable("onboardingCategories") {
            OnboardingCategoriesScreen(navController = navController, dataStore = dataStore)
        }
        composable("home") {
            HomeScreen(navController = navController, dataStore = dataStore)
        }
        composable("favorites") {
            FavoritesScreen(navController = navController, favoritesViewModel = favoritesViewModel)
        }
        composable("profile") {
            ProfileScreen(navController = navController)
        }
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("signup") {
            SignupScreen(navController = navController)
        }
        composable(
            route = "eventList?date={date}&location={location}",
            arguments = listOf(
                navArgument("date") { type = NavType.StringType; nullable = true },
                navArgument("location") { type = NavType.StringType; nullable = true }
            )
        ) { backStackEntry ->
            val date = backStackEntry.arguments?.getString("date")
            val location = backStackEntry.arguments?.getString("location") ?: ""
            EventListScreen(
                navController = navController,
                location = location,
                date = date,
                favoritesViewModel = favoritesViewModel
            )
        }

        composable(
            route = "eventScreen/{eventId}",
            arguments = listOf(navArgument("eventId") { type = NavType.IntType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getInt("eventId") ?: 0
            EventScreen(navController, eventId)
        }

    }
}
