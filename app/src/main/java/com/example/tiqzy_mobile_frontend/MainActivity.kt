package com.example.tiqzy_mobile_frontend

import BottomNavBar
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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
import com.example.tiqzy_mobile_frontend.data.database.dataStore
import com.example.tiqzy_mobile_frontend.viewmodel.EventViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tiqzy_Mobile_FrontEndTheme {
                val favoritesViewModel: FavoritesViewModel = viewModel()
                val eventViewModel: EventViewModel = viewModel()
                val dataStore = this@MainActivity.dataStore

                MainScreen(
                    favoritesViewModel = favoritesViewModel,
                    eventViewModel = eventViewModel,
                    dataStore = dataStore
                )
            }
        }
    }
}


@Composable
fun MainScreen(
    favoritesViewModel: FavoritesViewModel,
    eventViewModel: EventViewModel,
    dataStore: DataStore<Preferences>
) {
    val navController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    val noNavBarScreens = listOf("onboarding", "onboardingName", "login", "signup")

    Scaffold(
        bottomBar = {
            if (currentRoute !in noNavBarScreens) {
                BottomNavBar(navController = navController)
            }
        }
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            dataStore = dataStore,
            favoritesViewModel = favoritesViewModel,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
