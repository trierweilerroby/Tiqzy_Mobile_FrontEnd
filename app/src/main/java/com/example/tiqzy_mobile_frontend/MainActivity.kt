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
import androidx.navigation.compose.rememberNavController
import com.example.tiqzy_mobile_frontend.ui.navigation.AppNavHost
import com.example.tiqzy_mobile_frontend.ui.navigation.Screen
import com.example.tiqzy_mobile_frontend.ui.screens.EventListScreen
import com.example.tiqzy_mobile_frontend.ui.screens.HomeScreen
import com.example.tiqzy_mobile_frontend.ui.theme.Tiqzy_Mobile_FrontEndTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            MainScreen()
            /*Tiqzy_Mobile_FrontEndTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }*/
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun HomePage() {
    Tiqzy_Mobile_FrontEndTheme {
        Greeting("Android")
    }
}

@Composable
fun ProductPage(){
    Tiqzy_Mobile_FrontEndTheme {
        //EventListScreen()
    }
}
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}