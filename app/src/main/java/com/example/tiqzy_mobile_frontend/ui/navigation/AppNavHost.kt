package com.example.tiqzy_mobile_frontend.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tiqzy_mobile_frontend.data.database.dataStore
import com.example.tiqzy_mobile_frontend.ui.screens.*
import com.example.tiqzy_mobile_frontend.viewmodel.EventViewModel
import com.example.tiqzy_mobile_frontend.viewmodel.FavoritesViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    dataStore: DataStore<Preferences>,
    favoritesViewModel: FavoritesViewModel,
    eventViewModel: EventViewModel,
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
            val allEvents by eventViewModel.events.collectAsState(initial = emptyList())
            FavoritesScreen(navController = navController, favoritesViewModel = favoritesViewModel, allEvents = allEvents)
        }
        composable("profile") {
            ProfileScreen(navController = navController)
        }
        composable("tickets") {
            TicketsScreen(
                navController = navController,
                dataStore = LocalContext.current.dataStore)
        }
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("signup") {
            SignupScreen(
                navController = navController,
                dataStore = LocalContext.current.dataStore
            )
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
            route = "eventList?categories={categories}",
            arguments = listOf(
                navArgument("categories") { type = NavType.StringType; nullable = true }
            )
        ) { backStackEntry ->
            val categories = backStackEntry.arguments?.getString("categories") ?: ""
            EventListScreen(
                navController = navController,
                initialSelectedCategory = categories
            )
        }

        composable(
            route = "eventScreen/{eventId}",
            arguments = listOf(navArgument("eventId") { type = NavType.IntType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getInt("eventId") ?: 0
            EventScreen(navController, eventId)
        }

        composable("booking/{eventId}") { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId")?.toIntOrNull()
            if (eventId != null) {
                BookingScreen(navController, eventId)
            }
        }



    }
}
