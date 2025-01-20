package com.example.tiqzy_mobile_frontend.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.tiqzy_mobile_frontend.R
import com.example.tiqzy_mobile_frontend.ui.components.LoginCard
import com.example.tiqzy_mobile_frontend.ui.components.TicketCard
import com.example.tiqzy_mobile_frontend.ui.components.TicketsList
import com.example.tiqzy_mobile_frontend.viewmodel.OrderViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.firstOrNull

@Composable
fun TicketsScreen(
    navController: NavHostController,
    dataStore: DataStore<androidx.datastore.preferences.core.Preferences>,
    orderViewModel: OrderViewModel = hiltViewModel()
) {
    val nameKey = stringPreferencesKey("user_name")
    val userIdKey = stringPreferencesKey("user_id")

    var userId by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("User") }
    var isLoggedIn by remember { mutableStateOf(false) }

// Retrieve the current user from Firebase Authentication
    val currentUser = FirebaseAuth.getInstance().currentUser

// Read user ID and name from DataStore and handle Firebase Authentication
    LaunchedEffect(Unit) {
        dataStore.data
            .firstOrNull()?.let { preferences ->
                // Use Firebase userId if logged in, otherwise fallback to DataStore
                userId = currentUser?.uid ?: preferences[userIdKey] ?: ""
                userName = preferences[nameKey] ?: "Customer"
                isLoggedIn = userId.isNotEmpty()
            }

        // Debugging information
        if (userId.isEmpty()) {
            println("No authenticated user. User ID is empty.")
        } else {
            println("User authenticated. User ID: $userId")
        }
    }


    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TicketsHeader()

                if (!isLoggedIn) {
                    LoginCard(
                        onLoginClick = { navController.navigate("login") },
                        onSignupClick = { navController.navigate("signup") }
                    )
                } else {
                    TicketsList(
                        orderViewModel = orderViewModel,
                        userId = userId
                    )
                }
            }
        }
    }
}

@Composable
fun TicketsHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ticket),
                contentDescription = "Ticket Icon",
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "My Tickets",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
