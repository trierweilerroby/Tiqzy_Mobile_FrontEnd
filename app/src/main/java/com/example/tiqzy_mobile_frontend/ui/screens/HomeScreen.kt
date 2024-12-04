package com.example.tiqzy_mobile_frontend.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.tiqzy_mobile_frontend.ui.components.PreviewEventItem


@Composable
fun HomeScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        WelcomeAndPreview()
    }
}


@Composable
fun WelcomeAndPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Add some padding for spacing from the edges
        verticalArrangement = Arrangement.Top, // Aligns children at the top
        horizontalAlignment = Alignment.CenterHorizontally // Aligns children to the center horizontally
    ) {
        Text(
            text = "Welcome",
            style = MaterialTheme.typography.headlineMedium, // Optional styling
            modifier = Modifier.padding(bottom = 16.dp) // Space below the text
        )
        PreviewEventListScreen()
    }
}