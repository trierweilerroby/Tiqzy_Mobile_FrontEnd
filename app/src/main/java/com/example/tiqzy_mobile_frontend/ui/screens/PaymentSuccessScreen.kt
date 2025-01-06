package com.example.tiqzy_mobile_frontend.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PaymentSuccessScreen(
    title: String,
    name: String,
    price: String,
    bookingTime: String,
    tickets: Int,
    onShowQrCodeClick: () -> Unit,
    onAddToWalletClick: () -> Unit,
    onBrowseMoreEventsClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            //.background(Color(0xFFF5F5F5))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // Success Icon
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(Color(0xFF5B82D1), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "✔",
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Success Message
        Text(
            text = "Your payment was successful!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Booking Details Card
        BookingDetailsCard(
            title = title,
            name = name,
            price = price,
            bookingTime = bookingTime,
            tickets = tickets,
            onShowQrCodeClick = onShowQrCodeClick,
            onAddToWalletClick = onAddToWalletClick
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Browse More Events Button
        Button(
            onClick = onBrowseMoreEventsClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(Color(0xFF1D3557))
        ) {
            Text(text = "Browse More Events", color = Color.White)
        }
    }
}

@Composable
@Preview
fun PaymentSuccessScreenPreview() {
    PaymentSuccessScreen(
        title = "Lovers Canal Cruise",
        name = "Christian Bale",
        price = "€50.85",
        bookingTime = "August 16, 2023 · 10 AM",
        tickets = 3,
        onShowQrCodeClick = {},
        onAddToWalletClick = {},
        onBrowseMoreEventsClick = {}
    )
}
