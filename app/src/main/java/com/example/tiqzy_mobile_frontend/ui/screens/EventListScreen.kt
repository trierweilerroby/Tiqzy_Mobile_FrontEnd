package com.example.tiqzy_mobile_frontend.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tiqzy_mobile_frontend.ui.components.EventList
import com.example.tiqzy_mobile_frontend.viewmodel.EventViewModel
import com.example.tiqzy_mobile_frontend.viewmodel.FavoritesViewModel

@Composable
fun EventListScreen(
    navController: NavController,
    viewModel: EventViewModel = hiltViewModel(),
    favoritesViewModel: FavoritesViewModel = hiltViewModel(),
    location: String? = null,
    date: String? = null
) {
    LaunchedEffect(location, date) {
        viewModel.fetchEvents(date = date, venueCity = location)
    }

    val events by viewModel.events.collectAsState()

    Scaffold { innerPadding ->
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
        }
    }
}

