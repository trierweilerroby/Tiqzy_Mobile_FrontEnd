package com.example.tiqzy_mobile_frontend.ui.screens

import androidx.compose.foundation.layout.Box
import com.example.tiqzy_mobile_frontend.viewmodel.FavoritesViewModel
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tiqzy_mobile_frontend.data.model.Event
import com.example.tiqzy_mobile_frontend.ui.components.EventItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventListScreen(
    navController: NavController,
    location: String,
    date: String,
    favoritesViewModel: FavoritesViewModel
) {
    val events = listOf(
        Event(1, "Music Festival", "Enjoy live music.", "2024-12-10", 50,"haarlem", "https://career-advice.jobs.ac.uk/wp-content/uploads/Netherlands3-e1634207438966-1170x630.jpg.webp"),
        Event(2, "Art Exhibition", "Explore modern art.", "2024-12-12", 10,"haarlem","https://career-advice.jobs.ac.uk/wp-content/uploads/Netherlands3-e1634207438966-1170x630.jpg.webp"),
        Event(3, "Tech Conference", "Discover innovation.", "2024-12-15", 300,"Amsterdam","https://career-advice.jobs.ac.uk/wp-content/uploads/Netherlands3-e1634207438966-1170x630.jpg.webp")
    )
    val filteredEvents = events.filter { event ->
        event.location.contains(location, ignoreCase = true) && event.date == date
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Filtered Events") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
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
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                if (filteredEvents.isEmpty()) {
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
                    items(filteredEvents) { event ->
                        EventItem(event = event, favoritesViewModel = favoritesViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun PreviewEventListScreen(favoritesViewModel: FavoritesViewModel) {
    val mockEvents = listOf(
        Event(1, "Music Festival", "Enjoy live music.", "2024-12-10", 50,"haarlem","https://career-advice.jobs.ac.uk/wp-content/uploads/Netherlands3-e1634207438966-1170x630.jpg.webp"),
        Event(2, "Art Exhibition", "Explore modern art.", "2024-12-12", 10,"haarlem","https://career-advice.jobs.ac.uk/wp-content/uploads/Netherlands3-e1634207438966-1170x630.jpg.webp"),
        Event(3, "Tech Conference", "Discover innovation.", "2024-12-15", 300,"Amsterdam","https://career-advice.jobs.ac.uk/wp-content/uploads/Netherlands3-e1634207438966-1170x630.jpg.webp")
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(mockEvents) { event ->
            EventItem(event = event, favoritesViewModel = favoritesViewModel)
        }
    }
}




