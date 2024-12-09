package com.example.tiqzy_mobile_frontend.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.tiqzy_mobile_frontend.ui.components.SearchBar
import androidx.compose.runtime.remember
import com.example.tiqzy_mobile_frontend.viewmodel.FavoritesViewModel


@Composable
fun HomeScreen(navController: NavHostController, favoritesViewModel: FavoritesViewModel) {
    val (currentLocation, setCurrentLocation) = remember { mutableStateOf("") }
    val (selectedDate, setSelectedDate) = remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            HomeContent(
                navController = navController,
                favoritesViewModel = favoritesViewModel,
                currentLocation = currentLocation,
                selectedDate = selectedDate,
                onLocationChange = setCurrentLocation,
                onDateChange = setSelectedDate
            )
        }
    }
}


@Composable
fun HomeContent(
    navController: NavController,
    favoritesViewModel: FavoritesViewModel,
    currentLocation: String,
    selectedDate: String,
    onLocationChange: (String) -> Unit,
    onDateChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Add the SearchBar
        SearchBar(
            currentLocation = currentLocation,
            selectedDate = selectedDate,
            onLocationChange = onLocationChange,
            onDateChange = onDateChange,
            onSearchClick = {
                // Navigate to the filtered EventListScreen
                navController.navigate("eventList/$currentLocation/$selectedDate")
            }
        )

        // Add the event preview list below the SearchBar
        PreviewEventListScreen(favoritesViewModel = favoritesViewModel)
    }
}

