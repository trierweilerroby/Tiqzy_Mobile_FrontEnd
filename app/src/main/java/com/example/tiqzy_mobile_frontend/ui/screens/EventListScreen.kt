package com.example.tiqzy_mobile_frontend.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tiqzy_mobile_frontend.ui.components.EventList
import com.example.tiqzy_mobile_frontend.ui.components.FilterPopup
import com.example.tiqzy_mobile_frontend.ui.components.SortPopup
import com.example.tiqzy_mobile_frontend.viewmodel.EventViewModel
import com.example.tiqzy_mobile_frontend.viewmodel.FavoritesViewModel
import com.example.tiqzy_mobile_frontend.viewmodel.OnboardingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventListScreen(
    navController: NavController,
    viewModel: EventViewModel = hiltViewModel(),
    favoritesViewModel: FavoritesViewModel = hiltViewModel(),
    onboardingViewModel: OnboardingViewModel = hiltViewModel(),
    location: String? = null,
    date: String? = null
) {
    var isFilterPopupVisible by remember { mutableStateOf(false) }
    var isSortPopupVisible by remember { mutableStateOf(false) } // State for sort popup

    LaunchedEffect(location, date) {
        viewModel.fetchEvents(date = date, venueCity = location)
        viewModel.fetchCategories()
    }

    val events by viewModel.events.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Event List") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("home") {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back to Home")
                    }
                },
                actions = {
                    // Filter Button
                    IconButton(onClick = { isFilterPopupVisible = true }) {
                        Icon(Icons.Default.FilterList, contentDescription = "Filter Events")
                    }
                    // Sort Button
                    IconButton(onClick = { isSortPopupVisible = true }) {
                        Icon(Icons.AutoMirrored.Filled.Sort, contentDescription = "Sort Events")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            EventList(
                navController = navController,
                events = events,
                favoritesViewModel = favoritesViewModel
            )

            if (isFilterPopupVisible) {
                FilterPopup(
                    onDismissRequest = { isFilterPopupVisible = false },
                    viewModel = onboardingViewModel // Pass the OnboardingViewModel
                )
            }

            if (isSortPopupVisible) {
                SortPopup(
                    onDismissRequest = { isSortPopupVisible = false },
                    viewModel = viewModel // Pass the ViewModel here
                )
            }
        }
    }
}
