package com.example.tiqzy_mobile_frontend.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.navigation.NavController
import com.example.tiqzy_mobile_frontend.R
import com.example.tiqzy_mobile_frontend.data.database.DataStoreKeys
import com.example.tiqzy_mobile_frontend.data.repository.FirebaseRepository
import com.example.tiqzy_mobile_frontend.ui.components.LoginCard
import com.example.tiqzy_mobile_frontend.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(navController: NavController, dataStore: DataStore<Preferences>) {
    val scope = rememberCoroutineScope()

    // Fetch the current user's name from FirebaseAuth
    val userName = FirebaseAuth.getInstance().currentUser?.displayName


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
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    // Pass the user's name to the header
                    ProfileHeader(userName = userName)

                    LoginCard(
                        onLoginClick = { navController.navigate("login") },
                        onSignupClick = { navController.navigate("signup") }
                    )

                    ProfileMenuSection(navController)
                }

                // Logout Button
                Button(
                    onClick = {
                        scope.launch {
                            FirebaseAuth.getInstance().signOut()
                            // Set isLoggedIn to false
                            dataStore.edit { preferences ->
                                preferences[DataStoreKeys.IS_LOGGED_IN_KEY] = false
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text(text = "Logout", color = MaterialTheme.colorScheme.onErrorContainer)
                }
            }
        }
    }
}

@Composable
fun ProfileHeader(
    userName: String? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile Icon",
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = userName ?: "Welcome",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.bell),
            contentDescription = "Notifications",
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}


@Composable
fun ProfileMenuSection(navController: NavController) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        ProfileMenuItem(
            icon = R.drawable.heart,
            title = "Favorites",
            onClick = { navController.navigate("favorites") }
        )
        ProfileMenuItem(
            icon = R.drawable.clock,
            title = "All Orders",
            onClick = { navController.navigate("orders") }
        )
        ProfileMenuItem(
            icon = R.drawable.payment,
            title = "Payment",
            onClick = { navController.navigate("payment") }
        )
        ProfileMenuItem(
            icon = R.drawable.setting,
            title = "Settings",
            onClick = { navController.navigate("settings") }
        )
        ProfileMenuItem(
            icon = R.drawable.help,
            title = "Help & Support",
            onClick = { navController.navigate("help") }
        )
    }
}

@Composable
fun ProfileMenuItem(
    icon: Int,
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = title,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.arrow_right),
                contentDescription = "Arrow",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
