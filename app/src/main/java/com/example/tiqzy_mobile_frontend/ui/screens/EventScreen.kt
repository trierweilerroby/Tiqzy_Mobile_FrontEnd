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
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.tiqzy_mobile_frontend.data.model.Event
import com.example.tiqzy_mobile_frontend.ui.components.ShareButton

@Composable
fun EventScreen(
    navController: NavController,
    event: Event
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Event Image Section
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
                AsyncImage(
                    model = event.imageUrl,
                    contentDescription = "Event Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(bottom = 8.dp)
                )
                IconButton(
                    onClick = { /* Handle close */ },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.Black
                    )
                }
            }

            // Action Buttons below the image
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
            Text(
                text = event.location,
                style = TextStyle(fontSize = 16.sp, color = Color.Gray),
                modifier = Modifier.padding(top = 4.dp)
            )
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
                    .padding(top = 8.dp)
            )
        }
    }
}

// Preview
@Preview(showBackground = true)
@Composable
fun PreviewProductPageScreen() {
    val mockEvent = Event(
        id = 1,
        title = "Lovers Canal Cruise",
        description = "Join a 1-hour boat tour on the UNESCO Heritage canals of Amsterdam.",
        date = "2024-12-31",
        price = 1695,
        location = "Amsterdam Central Station",
        imageUrl = "https://career-advice.jobs.ac.uk/wp-content/uploads/Netherlands3-e1634207438966-1170x630.jpg.webp" // Replace with a real image URL for testing
    )
    EventScreen(navController = rememberNavController(), event = mockEvent)
}
