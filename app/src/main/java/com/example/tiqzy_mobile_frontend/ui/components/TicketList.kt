package com.example.tiqzy_mobile_frontend.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tiqzy_mobile_frontend.R
import com.example.tiqzy_mobile_frontend.viewmodel.OrderViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun TicketsList(
    orderViewModel: OrderViewModel,
    userId: String
) {
    val tickets by orderViewModel.tickets.collectAsState(initial = emptyList())
    var userName by remember { mutableStateOf("User") }

    // Fetch the user's display name
    LaunchedEffect(Unit) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        userName = currentUser?.displayName ?: currentUser?.email ?: "User"
    }

    LaunchedEffect(userId) {
        orderViewModel.fetchUserTickets(userId)
    }

    if (tickets.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No tickets found.")
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            tickets.forEach { ticket ->
                TicketCard(
                    title = ticket.name,
                    timeSlot = ticket.timeframe,
                    name = userName,
                    location = ticket.location,
                    imageUrl = ticket.imageURL
                )
            }
        }
    }
}