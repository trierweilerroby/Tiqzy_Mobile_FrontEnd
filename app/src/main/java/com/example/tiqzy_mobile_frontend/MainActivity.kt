package com.example.tiqzy_mobile_frontend

import BottomNavBar
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tiqzy_mobile_frontend.ui.navigation.AppNavHost
import com.example.tiqzy_mobile_frontend.viewmodel.FavoritesViewModel
import com.example.tiqzy_mobile_frontend.ui.theme.Tiqzy_Mobile_FrontEndTheme
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.tiqzy_mobile_frontend.viewmodel.EventViewModel
import dagger.hilt.android.AndroidEntryPoint

// Define a singleton DataStore instance for saving user preferences.
// The name "user_prefs" identifies the preference storage file.
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

// MainActivity serves as the entry point of the application.
// It is annotated with @AndroidEntryPoint for dependency injection using Hilt.
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Apply the app's theme
            Tiqzy_Mobile_FrontEndTheme {
                // Create or retrieve the instance of FavoritesViewModel using ViewModel
                val favoritesViewModel: FavoritesViewModel = viewModel()

                // Create or retrieve the instance of EventViewModel using ViewModel
                val eventViewModel: EventViewModel = viewModel()

                // Access the singleton DataStore instance for storing user preferences
                val dataStore = this@MainActivity.dataStore

                // Call the MainScreen composable function and pass required dependencies
                MainScreen(
                    favoritesViewModel = favoritesViewModel,
                    eventViewModel = eventViewModel,
                    dataStore = dataStore
                )
                //launch test
                //Text(text = "App Launches!", modifier = Modifier.fillMaxSize())
            }
        }
    }
}

// MainScreen is the root composable function that holds the app's navigation and layout.
@Composable
fun MainScreen(
    favoritesViewModel: FavoritesViewModel,
    eventViewModel: EventViewModel,
    dataStore: DataStore<Preferences>
) {
    // Create a navigation controller to manage navigation between screens
    val navController = rememberNavController()

    // Observe the current route in the navigation back stack
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    // Define screens where the BottomNavBar should NOT appear
    val noNavBarScreens = listOf("onboarding", "onboardingName", "login", "signup")

    // Scaffold is the main container providing slots for UI elements like a bottom bar
    Scaffold(
        bottomBar = {
            // Conditionally display the BottomNavBar if the current route is not in the noNavBarScreens list
            if (currentRoute !in noNavBarScreens) {
                BottomNavBar(navController = navController)
            }
        }
    ) { innerPadding ->
        // AppNavHost manages the navigation and renders the appropriate screens
        AppNavHost(
            navController = navController,
            dataStore = dataStore,                   // Pass DataStore for user preferences
            favoritesViewModel = favoritesViewModel, // Pass the FavoritesViewModel
            //eventViewModel = eventViewModel,         // Pass the EventViewModel
            modifier = Modifier.padding(innerPadding) // Apply padding for content below the AppBar/BottomNavBar
        )
    }
}
