package com.example.tiqzy_mobile_frontend.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.tiqzy_mobile_frontend.R


@Composable
fun OnboardingNameScreen(navController: NavController, dataStore: DataStore<Preferences>) {
    val nameKey = stringPreferencesKey("user_name")
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp)
            ) {
                // Static Image
                Image(
                    painter = painterResource(id = R.drawable.onboarding),
                    contentDescription = "Illustration",
                    modifier = Modifier
                        .size(200.dp)
                )
                // Header Text
                Text(
                    text = "What's your name?",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Input Field
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = { Text("Name...") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Progress Bar
                    LinearProgressIndicator(
                        progress = 2f / 3,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(12.dp),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = Color.Gray.copy(alpha = 0.3f),
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                // Next Button
                Button(
                    onClick = {
                        scope.launch {
                            dataStore.edit { settings ->
                                settings[nameKey] = name
                            }
                            navController.navigate("onboardingCategories")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Next")
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Skip Navigation
                TextButton(
                    onClick = {
                        scope.launch {
                            dataStore.edit { settings ->
                                settings.remove(nameKey)
                            }
                            navController.navigate("onboardingCategories")
                        }
                    }
                ) {
                    Text("Skip")
                }
            }
        }
    }
}
