package com.example.tiqzy_mobile_frontend.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.tiqzy_mobile_frontend.data.model.Event
import com.example.tiqzy_mobile_frontend.ui.components.EventItem
import com.example.tiqzy_mobile_frontend.viewmodel.FavoritesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    navController: NavController,
    favoritesViewModel: FavoritesViewModel,
    allEvents: List<Event>
) {
    val favoriteIds by favoritesViewModel.favorites.collectAsState(initial = emptySet())
    val favoriteEvents = remember(favoriteIds, allEvents) {
        val mappedEvents = allEvents.filter { event -> favoriteIds.contains(event.id.toString()) }
        mappedEvents
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favorites") },
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (favoriteEvents.isEmpty()) {
                Text(
                    text = "No favorites added yet.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            } else {
                LazyColumn {
                    items(favoriteEvents) { event ->
                        EventItem(
                            event = event,
                            favoritesViewModel = favoritesViewModel,
                            onClick = { navController.navigate("eventScreen/${event.id}") }
                        )
                    }
                }
            }
        }
    }
}