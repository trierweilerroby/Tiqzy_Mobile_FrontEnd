package com.example.tiqzy_mobile_frontend.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tiqzy_mobile_frontend.data.model.Event
import com.example.tiqzy_mobile_frontend.viewmodel.FavoritesViewModel

@Composable
fun EventList(
    navController: NavController,
    events: List<Event>,
    favoritesViewModel: FavoritesViewModel,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        //println("$events")
        if (events.isEmpty()) {
            item {
                Text(
                    text = "No events found for the selected filters.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            items(events) { event ->
                EventItem(
                    event = event,
                    favoritesViewModel = favoritesViewModel,
                    onClick = {
                        navController.navigate("eventScreen/${event.id}")
                    }
                )
            }
        }
    }
}
