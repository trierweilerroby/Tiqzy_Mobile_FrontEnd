package com.example.tiqzy_mobile_frontend

import BottomNavBar
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.tiqzy_mobile_frontend.ui.navigation.AppNavHost
import com.example.tiqzy_mobile_frontend.ui.navigation.Screen
import com.example.tiqzy_mobile_frontend.ui.screens.EventListScreen
import com.example.tiqzy_mobile_frontend.ui.screens.HomeScreen
import com.example.tiqzy_mobile_frontend.ui.theme.Tiqzy_Mobile_FrontEndTheme
import com.example.tiqzy_mobile_frontend.viewmodel.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val favoritesViewModel: FavoritesViewModel = viewModel()

            MainScreen(favoritesViewModel = favoritesViewModel)
        }
    }
}

@Composable
fun MainScreen(favoritesViewModel: FavoritesViewModel) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            favoritesViewModel = favoritesViewModel,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
