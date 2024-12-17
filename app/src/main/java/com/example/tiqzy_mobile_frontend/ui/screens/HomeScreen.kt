package com.example.tiqzy_mobile_frontend.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.tiqzy_mobile_frontend.ui.components.SearchBar
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.compose.rememberNavController
import com.example.tiqzy_mobile_frontend.viewmodel.FavoritesViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun HomeScreen(navController: NavHostController, dataStore: DataStore<Preferences>) {
    val nameKey = stringPreferencesKey("user_name")
    var userName by remember { mutableStateOf("User") }
    val (currentLocation, setCurrentLocation) = remember { mutableStateOf("") }
    val (selectedDate, setSelectedDate) = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val savedName = runBlocking {
            dataStore.data.first()[nameKey] ?: "Customer"
        }
        userName = savedName
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Welcome $userName!",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(top = 15.dp)
                )

                HomeContent(
                    navController = navController,
                    currentLocation = currentLocation,
                    selectedDate = selectedDate,
                    onLocationChange = setCurrentLocation,
                    onDateChange = setSelectedDate
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Button to Navigate to Event List
                Button(onClick = {
                    val encodedLocation = encodeForNavigation(currentLocation)
                    val encodedDate = encodeForNavigation(selectedDate)
                    navController.navigate("eventList/$encodedLocation/$encodedDate")
                }) {
                    Text("Go to Events")
                }
            }
        }
    }
}

@Composable
fun HomeContent(
    navController: NavController,
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

        // Add the SearchBar
        SearchBar(
            currentLocation = currentLocation,
            selectedDate = selectedDate,
            onLocationChange = onLocationChange,
            onDateChange = onDateChange,
            onSearchClick = { location, date ->
                if (location.isNotEmpty() && date.isNotEmpty()) {
                    val encodedLocation = encodeForNavigation(location)
                    val encodedDate = encodeForNavigation(date)
                    navController.navigate("eventList/$encodedLocation/$encodedDate")
                } else {
                    println("error")
                }
            }
        )

    }
}

fun encodeForNavigation(input: String): String {
    return URLEncoder.encode(input, StandardCharsets.UTF_8.toString())
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(navController = NavHostController(androidx.compose.ui.platform.LocalContext.current),
        dataStore = object : DataStore<Preferences> {
            override val data: kotlinx.coroutines.flow.Flow<Preferences>
                get() = kotlinx.coroutines.flow.flowOf(androidx.datastore.preferences.core.preferencesOf())
            override suspend fun updateData(transform: suspend (Preferences) -> Preferences): Preferences {
                return androidx.datastore.preferences.core.preferencesOf()
            }
        }
    )
}

