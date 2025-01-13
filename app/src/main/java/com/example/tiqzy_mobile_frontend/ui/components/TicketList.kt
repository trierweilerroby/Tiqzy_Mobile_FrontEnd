package com.example.tiqzy_mobile_frontend.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tiqzy_mobile_frontend.R

@Composable
fun TicketsList() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TicketCard(
            title = "Canal Cruise",
            date = "25/11/24",
            imageUrl = "https://img.freepik.com/premium-vector/red-grunge-rubber-stamp-with-word-replace-it_545399-3668.jpg" // Replace with actual image resource ID
        )
        TicketCard(
            title = "Music Concert",
            date = "30/11/24",
            imageUrl = "https://img.freepik.com/premium-vector/red-grunge-rubber-stamp-with-word-replace-it_545399-3668.jpg" // Replace with actual image resource ID
        )
    }
}

