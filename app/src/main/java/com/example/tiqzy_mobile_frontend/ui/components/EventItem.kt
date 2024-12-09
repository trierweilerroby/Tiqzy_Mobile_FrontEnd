package com.example.tiqzy_mobile_frontend.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tiqzy_mobile_frontend.data.model.Event
import com.example.tiqzy_mobile_frontend.viewmodel.FavoritesViewModel

@Composable
fun EventItem(
    event: Event,
    favoritesViewModel: FavoritesViewModel,
    modifier: Modifier = Modifier
) {
    val isFavorite = favoritesViewModel.isFavorite(event) // Check if the event is a favorite

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Event details
            Column(modifier = Modifier.weight(1f)) {
                Text(text = event.title, style = MaterialTheme.typography.titleMedium)
                Text(text = event.description, style = MaterialTheme.typography.bodySmall)
                Text(text = "Date: ${event.date}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Price: ${event.price}€", style = MaterialTheme.typography.bodyMedium)
            }

            // Heart icon for toggling favorite
            Icon(
                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = if (isFavorite) "Unfavorite" else "Favorite",
                tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { favoritesViewModel.toggleFavorite(event) }
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewEventItem() {
    // Mock data for an event
    val mockEvent = Event(
        id = 1,
        title = "Sample Event",
        description = "This is a description of the event.",
        date = "2024-12-05",
        price = 10
    )

    // Mock FavoritesViewModel
    val mockFavoritesViewModel = object : FavoritesViewModel() {
        private val mockFavorites = mutableStateListOf<Event>()
        override val favorites: List<Event> get() = mockFavorites

        override fun toggleFavorite(event: Event) {
            if (mockFavorites.contains(event)) {
                mockFavorites.remove(event)
            } else {
                mockFavorites.add(event)
            }
        }

        override fun isFavorite(event: Event): Boolean {
            return mockFavorites.contains(event)
        }
    }

    // Display the EventItem with mock data
    EventItem(event = mockEvent, favoritesViewModel = mockFavoritesViewModel)
}
