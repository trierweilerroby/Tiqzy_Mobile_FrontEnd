package com.example.tiqzy_mobile_frontend.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    venueCity: String? = null,
    date: String? = null,
    categories: List<String>? = null,
    sort: String? = null,
    initialSelectedCategory: String? = null
) {
    var isFilterPopupVisible by remember { mutableStateOf(false) }
    var isSortPopupVisible by remember { mutableStateOf(false) }

    // Initialize selectedCategories with categories or initialSelectedCategory
    var selectedCategories by remember {
        mutableStateOf(
            categories ?: initialSelectedCategory?.let { listOf(it) } ?: emptyList()
        )
    }

    val events by viewModel.events.collectAsState()
    val sortKey by viewModel.sortKey.collectAsState()

    // Update selectedCategories dynamically when categories parameter changes
    LaunchedEffect(categories) {
        if (categories != null) {
            selectedCategories = categories
        }
    }

    // Fetch events when filters change
    LaunchedEffect(selectedCategories, sortKey, venueCity, date) {
        println("Fetching with filters: Location=$venueCity, Date=$date, Categories=$selectedCategories, Sort=$sortKey")
        viewModel.fetchEvents(
            date = date,
            venueCity = venueCity,
            categories = selectedCategories,
            sort = sortKey
        )
    }

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
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back to Home")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Sort and Filter Buttons Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CustomButton(
                    text = "Sort",
                    icon = Icons.Default.Sort,
                    onClick = { isSortPopupVisible = true }
                )
                CustomButton(
                    text = "Filter",
                    icon = Icons.Default.FilterList,
                    onClick = { isFilterPopupVisible = true }
                )
            }

            // Event List
            Box(modifier = Modifier.fillMaxSize()) {
                EventList(
                    navController = navController,
                    events = events,
                    favoritesViewModel = favoritesViewModel
                )

                // Filter Popup
                if (isFilterPopupVisible) {
                    FilterPopup(
                        onDismissRequest = { isFilterPopupVisible = false },
                        preSelectedCategories = selectedCategories, // Pass merged categories
                        onApplyFilters = { newCategories ->
                            selectedCategories = newCategories // Update selectedCategories
                        }
                    )
                }

                // Sort Popup
                if (isSortPopupVisible) {
                    SortPopup(
                        onDismissRequest = { isSortPopupVisible = false },
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun CustomButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
        modifier = Modifier.height(40.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxHeight()
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "$text Icon",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary)
            )
        }
    }
}