package com.example.tiqzy_mobile_frontend.ui.screens

import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tiqzy_mobile_frontend.ui.components.ExploreCategories
import com.example.tiqzy_mobile_frontend.ui.components.SearchBar
import com.example.tiqzy_mobile_frontend.viewmodel.OnboardingViewModel
import kotlinx.coroutines.flow.first
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun HomeScreen(
    navController: NavHostController,
    dataStore: DataStore<Preferences>,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val nameKey = stringPreferencesKey("user_name")
    var userName by remember { mutableStateOf("User") }

    val categoriesKey = stringPreferencesKey("user_categories")
    val userSelectedCategories = remember { mutableStateOf<List<String>>(emptyList()) }

    val categories by viewModel.categories.collectAsState()

    val (currentLocation, setCurrentLocation) = remember { mutableStateOf("") }
    val (selectedDate, setSelectedDate) = remember { mutableStateOf("") }

    // Load user data and selected categories
    LaunchedEffect(Unit) {
        userName = dataStore.data.first()[nameKey] ?: "Customer"

        val selectedCategoriesString = dataStore.data.first()[categoriesKey] ?: ""
        userSelectedCategories.value = selectedCategoriesString.split(",").map { it.trim() }
    }

    // Sort categories: user-selected first, others follow
    val sortedCategories = remember(categories, userSelectedCategories.value) {
        val selected = categories.filter { userSelectedCategories.value.contains(it.name) }
        val remaining = categories.filterNot { userSelectedCategories.value.contains(it.name) }
        selected + remaining
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
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.fillMaxSize()
            ) {
                // Welcome message
                Text(
                    text = "Welcome $userName!",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(top = 15.dp)
                )

                // Home search
                HomeSearch(
                    navController = navController,
                    currentLocation = currentLocation,
                    selectedDate = selectedDate,
                    onLocationChange = setCurrentLocation,
                    onDateChange = setSelectedDate
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Home content
                HomeContent(sortedCategories = sortedCategories)
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
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SearchBar(
            currentLocation = currentLocation,
            selectedDate = selectedDate,
            onLocationChange = onLocationChange,
            onDateChange = onDateChange,
            onSearchClick = { location, date ->
                val encodedLocation = location?.let { URLEncoder.encode(it, "UTF-8") } ?: ""
                val encodedDate = date?.let { URLEncoder.encode(it, "UTF-8") } ?: ""
                navController.navigate("eventList?date=$encodedDate&location=$encodedLocation")
            }

        )
    }
}

@Composable
fun HomeContent(sortedCategories: List<com.example.tiqzy_mobile_frontend.ui.components.Category>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // Explore Categories Component
        ExploreCategories(categories = sortedCategories)
    }
}

fun encodeForNavigation(input: String): String {
    return URLEncoder.encode(input, StandardCharsets.UTF_8.toString())
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(
        navController = rememberNavController(),
        dataStore = object : DataStore<Preferences> {
            override val data: kotlinx.coroutines.flow.Flow<Preferences>
                get() = kotlinx.coroutines.flow.flowOf(androidx.datastore.preferences.core.preferencesOf())
            override suspend fun updateData(transform: suspend (Preferences) -> Preferences): Preferences {
                return androidx.datastore.preferences.core.preferencesOf()
            }
        }
    )
}
