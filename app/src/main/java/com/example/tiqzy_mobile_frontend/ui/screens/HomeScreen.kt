package com.example.tiqzy_mobile_frontend.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Doorbell
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tiqzy_mobile_frontend.data.model.Category
import com.example.tiqzy_mobile_frontend.data.model.City
import com.example.tiqzy_mobile_frontend.ui.components.ExploreCategories
import com.example.tiqzy_mobile_frontend.ui.components.ExploreCities
import com.example.tiqzy_mobile_frontend.ui.components.SearchBar
import com.example.tiqzy_mobile_frontend.viewmodel.EventViewModel
import com.example.tiqzy_mobile_frontend.viewmodel.OnboardingViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun HomeScreen(
    navController: NavHostController,
    dataStore: DataStore<Preferences>,
    viewModel: OnboardingViewModel = hiltViewModel(),
    eventViewModel: EventViewModel = hiltViewModel()
) {
    val nameKey = stringPreferencesKey("user_name")
    var userName by remember { mutableStateOf("User") }

    val logedUser = FirebaseAuth.getInstance().currentUser?.displayName

    val categoriesKey = stringPreferencesKey("user_categories")
    val userSelectedCategories = remember { mutableStateOf<List<String>>(emptyList()) }

    val categories by viewModel.categories.collectAsState()

    val cities by eventViewModel.cities.collectAsState()

    val (currentLocation, setCurrentLocation) = remember { mutableStateOf("") }
    val (selectedDate, setSelectedDate) = remember { mutableStateOf("") }

    var filteredCategories by remember { mutableStateOf(categories) }

    val isLoggedIn by dataStore.data
        .map { preferences -> preferences[booleanPreferencesKey("is_logged_in")] ?: false }
        .collectAsState(initial = false)

    LaunchedEffect(Unit) {
        val storedName = dataStore.data.first()[nameKey]
        userName = storedName ?: "Customer"

        val selectedCategoriesString = dataStore.data.first()[categoriesKey] ?: ""
        userSelectedCategories.value = selectedCategoriesString.split(",").map { it.trim() }
    }

    // Sort categories: user-selected first, others follow
    val sortedCategories = remember(categories, userSelectedCategories.value) {
        val selected = categories.filter { userSelectedCategories.value.contains(it.name) }
        val remaining = categories.filterNot { userSelectedCategories.value.contains(it.name) }
        selected + remaining
    }

    // Filter categories based on location and date
    LaunchedEffect(currentLocation, selectedDate) {
        filteredCategories = if (currentLocation.isNotEmpty() || selectedDate.isNotEmpty()) {
            categories.filter { category ->
                val matchesLocation = currentLocation.isEmpty() || category.name.contains(currentLocation, ignoreCase = true)
                val matchesDate = selectedDate.isEmpty()
                matchesLocation && matchesDate
            }
        } else {
            categories
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Welcome Header
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Holland Welcome",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = if (isLoggedIn) "Welcome back, $logedUser!" else "Welcome, Guest!",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifications",
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Home Search Section
            HomeSearch(
                navController = navController,
                currentLocation = currentLocation,
                selectedDate = selectedDate,
                onLocationChange = setCurrentLocation,
                onDateChange = setSelectedDate
            )

            // Explore Cities and Categories
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.Start
            ) {

                ExploreCategories(navController = navController, categories = sortedCategories)

                ExploreCities(navController = navController, cities = cities)

            }
        }
    }
}

@Composable
fun HomeSearch(
    navController: NavHostController,
    currentLocation: String,
    selectedDate: String,
    onLocationChange: (String) -> Unit,
    onDateChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar(
            currentLocation = currentLocation,
            selectedDate = selectedDate,
            onLocationChange = onLocationChange,
            onDateChange = onDateChange,
            onSearchClick = { location, date ->
                val encodedLocation = location?.let { URLEncoder.encode(it, StandardCharsets.UTF_8.name()) } ?: ""
                val encodedDate = date?.let { URLEncoder.encode(it, StandardCharsets.UTF_8.name()) } ?: ""
                navController.navigate("eventList?date=$encodedDate&location=$encodedLocation")
            }
        )
    }
}

