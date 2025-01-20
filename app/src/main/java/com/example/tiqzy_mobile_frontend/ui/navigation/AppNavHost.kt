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
import com.example.tiqzy_mobile_frontend.data.database.DataStoreSingleton
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
    modifier: Modifier = Modifier,
    isNotFirstTimer: Boolean
) {
    NavHost(
        navController = navController,
        startDestination = if (isNotFirstTimer) "home" else "onboarding",
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
            ProfileScreen(navController = navController, dataStore = LocalContext.current.dataStore)
        }
        composable("tickets") {
            TicketsScreen(
                navController = navController,
                dataStore = LocalContext.current.dataStore
            )
        }
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("signup") {
            SignupScreen(
                navController = navController
            )
        }

        composable(
            route = "eventList?date={date}&venueCity={venueCity}&categories={categories}&sort={sort}",
            arguments = listOf(
                navArgument("date") { type = NavType.StringType; nullable = true },
                navArgument("venueCity") { type = NavType.StringType; nullable = true },
                navArgument("categories") { type = NavType.StringType; nullable = true },
                navArgument("sort") { type = NavType.StringType; nullable = true }
            )
        ) { backStackEntry ->
            val date = backStackEntry.arguments?.getString("date")
            val venueCity = backStackEntry.arguments?.getString("venueCity")
            val categories = backStackEntry.arguments?.getString("categories")
                ?.let { java.net.URLDecoder.decode(it, java.nio.charset.StandardCharsets.UTF_8.name()) }
                ?.split(",")

            val sort = backStackEntry.arguments?.getString("sort")
            EventListScreen(
                navController = navController,
                venueCity = venueCity,
                date = date,
                categories = categories,
                sort = sort
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

        composable("settings") {
            SettingsScreen(
                dataStoreSingleton = DataStoreSingleton(navController.context),
                onPreferencesReset = {
                    navController.navigate("onboarding") {
                        popUpTo(0) { inclusive = true }
                    }
                },
                navController = navController
            )
        }


    }
}
