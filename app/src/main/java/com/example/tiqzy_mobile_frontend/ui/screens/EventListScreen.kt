package com.example.tiqzy_mobile_frontend.ui.screens

import FavoritesViewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.tiqzy_mobile_frontend.data.model.Event
import com.example.tiqzy_mobile_frontend.ui.components.EventItem

@Composable
fun EventListScreen(events: List<Event>, favoritesViewModel: FavoritesViewModel) {
    LazyColumn {
        items(events) { event ->
            EventItem(event = event, favoritesViewModel = favoritesViewModel)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewEventListScreen() {
    // Mock list of events
    val mockEvents = listOf(
        Event(1, "Music Festival", "Enjoy a weekend of live music.", "2024-12-10", 50),
        Event(2, "Art Exhibition", "Explore modern art from around the world.", "2024-12-12", 10),
        Event(3, "Tech Conference", "Discover the latest in technology and innovation.", "2024-12-15", 300)
    )

    // Mock FavoritesViewModel for the preview
    val mockFavoritesViewModel = FavoritesViewModel()

    EventListScreen(events = mockEvents, favoritesViewModel = mockFavoritesViewModel)
}

