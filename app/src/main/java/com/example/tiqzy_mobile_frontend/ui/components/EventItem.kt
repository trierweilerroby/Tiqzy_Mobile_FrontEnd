package com.example.tiqzy_mobile_frontend.ui.components


import com.example.tiqzy_mobile_frontend.viewmodel.FavoritesViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tiqzy_mobile_frontend.data.model.Event
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun EventItem(
    event: Event,
    favoritesViewModel: FavoritesViewModel, // Pass the ViewModel here
    modifier: Modifier = Modifier
) {
    // Observe whether the event is in the favorites list
    val isFavorite = favoritesViewModel.favorites.contains(event)
    var showPopup by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Box {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                // Event name
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.titleMedium
                )

                // Location Row with Icon
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = "Location Icon",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = event.location,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Event description
                Text(
                    text = event.description,
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Event timings
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Start: ${event.date}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "End: ${event.date}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Event price
                Text(
                    text = "Price: ${event.price}€",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Favorite heart in the top-right corner
            Icon(
                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = if (isFavorite) "Unfavorite" else "Favorite",
                tint = if (isFavorite) androidx.compose.ui.graphics.Color.Red else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .clickable {
                        favoritesViewModel.toggleFavorite(event) // Add/remove from favorites
                        showPopup = true
                        coroutineScope.launch {
                            delay(1500) // Dismiss popup after 1.5 seconds
                            showPopup = false
                        }
                    }
            )

            if (showPopup) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                ) {
                    Text(
                        text = if (isFavorite) "Removed from Favorites" else "Added to Favorites",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(8.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
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
        price = 10,
        location = "Haarlem",
        imageUrl = "https://career-advice.jobs.ac.uk/wp-content/uploads/Netherlands3-e1634207438966-1170x630.jpg.webp"
    )

    val mockFavoritesViewModel = FavoritesViewModel()
    EventItem(event = mockEvent, favoritesViewModel = mockFavoritesViewModel)
}

