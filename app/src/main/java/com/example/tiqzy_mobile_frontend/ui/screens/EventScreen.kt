package com.example.tiqzy_mobile_frontend.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.tiqzy_mobile_frontend.data.model.Event
import com.example.tiqzy_mobile_frontend.ui.components.ShareButton
import com.example.tiqzy_mobile_frontend.viewmodel.EventViewModel

@Composable
fun EventScreen(
    navController: NavController,
    eventId: Int,
    viewModel: EventViewModel = hiltViewModel()
) {
    // Observe the selected event
    val event by viewModel.selectedEvent.collectAsState()

    // Fetch the event when the screen is loaded
    LaunchedEffect(eventId) {
        viewModel.fetchEventById(eventId)
    }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (event != null) {
                EventDetailsContent(navController = navController, event = event!!)
            } else {
                Text(
                    text = "Loading...",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun EventDetailsContent(navController: NavController, event: Event) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        // Event Image Section
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
            AsyncImage(
                model = event.image?.url,
                contentDescription = "Event Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(bottom = 8.dp)
            )
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.Black
                )
            }
        }

        // Action Buttons and Event Details
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* Handle like */ }) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Like",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            IconButton(onClick = { /* Handle location */ }) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            ShareButton(eventLink = "https://www.example.com/event/${event.id}")
        }

        // Event Title and Details
        Text(
            text = event.title,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 8.dp)
        )
        event.venue?.let {
            it.city?.let { it1 ->
                Text(
                    text = it1,
                    style = TextStyle(fontSize = 16.sp, color = Color.Gray),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
        Text(
            text = "from ${event.price / 100.0}€", // Convert price to Euros
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = 4.dp)
        )

        // Check Availability Button
        Button(
            onClick = { /* Handle check availability */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text("Check availability")
        }

        // Description Section
        Text(
            text = event.description,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .background(color = Color(0xFFEEF5FF))
                .padding(8.dp)
        )
    }
}

