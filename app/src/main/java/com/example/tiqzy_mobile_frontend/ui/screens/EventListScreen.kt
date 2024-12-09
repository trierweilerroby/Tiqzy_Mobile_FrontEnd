package com.example.tiqzy_mobile_frontend.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.tiqzy_mobile_frontend.data.model.Event
import com.example.tiqzy_mobile_frontend.ui.components.EventItem

@Composable
fun EventListScreen(events: List<Event>) {
    LazyColumn {
        items(events) { event ->
            EventItem(event = event)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEventListScreen() {
    // Mock list of events
    val mockEvents = listOf(
        Event(
            id = 1,
            title = "Music Festival",
            description = "Enjoy a weekend of live music.",
            date = "2024-12-10",
            price = 50
        ),
        Event(
            id = 2,
            title = "Art Exhibition",
            description = "Explore modern art from around the world.",
            date = "2024-12-12",
            price = 10
        ),
        Event(
            id = 3,
            title = "Tech Conference",
            description = "Discover the latest in technology and innovation.",
            date = "2024-12-15",
            price = 300
        )
    )

    EventListScreen(events = mockEvents)
}
