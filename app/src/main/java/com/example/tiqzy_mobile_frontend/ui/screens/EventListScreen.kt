package com.example.tiqzy_mobile_frontend.ui.screens

import com.example.tiqzy_mobile_frontend.viewmodel.FavoritesViewModel
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.tiqzy_mobile_frontend.data.model.Event
import com.example.tiqzy_mobile_frontend.ui.components.EventItem


@Composable
fun EventListScreen(
    navController: NavController,
    location: String,
    date: String,
    favoritesViewModel: FavoritesViewModel
) {
    val events = listOf(
        Event(1, "Music Festival", "Enjoy live music.", "2024-12-10", 50),
        Event(2, "Art Exhibition", "Explore modern art.", "2024-12-12", 10),
        Event(3, "Tech Conference", "Discover innovation.", "2024-12-15", 300)
    ).filter {
        it.title.contains(location, ignoreCase = true) && it.date == date
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(events) { event ->
            EventItem(event = event, favoritesViewModel = favoritesViewModel)
        }
    }
}

@Composable
fun PreviewEventListScreen(favoritesViewModel: FavoritesViewModel) {
    val mockEvents = listOf(
        Event(1, "Music Festival", "Enjoy live music.", "2024-12-10", 50),
        Event(2, "Art Exhibition", "Explore modern art.", "2024-12-12", 10),
        Event(3, "Tech Conference", "Discover innovation.", "2024-12-15", 300)
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(mockEvents) { event ->
            EventItem(event = event, favoritesViewModel = favoritesViewModel)
        }
    }
}




