package com.example.tiqzy_mobile_frontend.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tiqzy_mobile_frontend.ui.screens.*
import com.example.tiqzy_mobile_frontend.viewmodel.EventViewModel
import com.example.tiqzy_mobile_frontend.viewmodel.FavoritesViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    dataStore: DataStore<Preferences>,
    favoritesViewModel: FavoritesViewModel,
    //eventViewModel: EventViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "onboarding", //Screen.Onboarding.route,
        modifier = modifier
    ) {
        composable(Screen.Onboarding.route) {
            Text("onboardingScreen")
            //OnboardingScreen(navController = navController)
        }
        // Onboarding Name Screen
        composable(Screen.OnboardingName.route) {
            OnboardingNameScreen(navController = navController, dataStore = dataStore)
        }

        composable(Screen.OnboardingCategories.route) {
            OnboardingCategoriesScreen(navController = navController, dataStore = dataStore)
        }

        // Home Screen with DataStore
        composable(Screen.Home.route) {
            HomeScreen(navController = navController, dataStore = dataStore)
        }

        // Event List Screen
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

        /* Reservation Screen
        composable(
            route = "reservation/{eventName}/{location}/{imageUrl}/{basePrice}",
            arguments = listOf(
                navArgument("eventName") { type = NavType.StringType },
                navArgument("location") { type = NavType.StringType },
                navArgument("imageUrl") { type = NavType.StringType },
                navArgument("basePrice") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val eventName = backStackEntry.arguments?.getString("eventName") ?: ""
            val location = backStackEntry.arguments?.getString("location") ?: ""
            val imageUrl = backStackEntry.arguments?.getString("imageUrl") ?: ""
            val basePrice = backStackEntry.arguments?.getString("basePrice")?.toDoubleOrNull() ?: 0.0

            ReservationScreen(
                eventName = eventName,
                location = location,
                imageUrl = imageUrl,
                basePrice = basePrice,
                onBackClicked = { navController.popBackStack() },
                onBuyClicked = {
                    // Handle Buy button logic, or navigate to a confirmation screen
                }
            )
        }*/

        // Other Screens
        composable(Screen.Tickets.route) {
            TicketsScreen(navController = navController)
        }
        composable(Screen.Favorites.route) {
            FavoritesScreen(navController = navController, favoritesViewModel = favoritesViewModel)
        }
        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }

        // Authentication Screens
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.Signup.route) {
            SignupScreen(navController = navController)
        }
    }
}
