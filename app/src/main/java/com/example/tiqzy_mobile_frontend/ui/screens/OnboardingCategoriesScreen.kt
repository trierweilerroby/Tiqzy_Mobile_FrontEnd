package com.example.tiqzy_mobile_frontend.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tiqzy_mobile_frontend.ui.components.CategoriesList
import com.example.tiqzy_mobile_frontend.viewmodel.OnboardingViewModel
import kotlinx.coroutines.launch

@Composable
fun OnboardingCategoriesScreen(
    navController: NavController,
    dataStore: DataStore<Preferences>,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    // Observe categories from ViewModel
    val categories = viewModel.categories.collectAsState(initial = emptyList())

    // Remember the selected categories
    val selectedCategories = remember { mutableStateListOf<String>() }
    val scope = rememberCoroutineScope()
    val selectedCategoriesKey = stringPreferencesKey("user_categories")

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Skip Button
                OutlinedButton(onClick = { navController.navigate("home") }) {
                    Text("Skip")
                }
                // Let's Go Button
                Button(onClick = {
                    scope.launch {
                        val selectedCategoriesString = selectedCategories.joinToString(",")
                        dataStore.edit { preferences ->
                            preferences[selectedCategoriesKey] = selectedCategoriesString
                        }
                        navController.navigate("home")
                    }
                }) {
                    Text("Let's Go!")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            //verticalArrangement = Arrangement.SpaceBetween
        ) {

            //'Text(viewModel.message)

            // Header Text
            Text(
                text = "Tell us what you like.",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.height(230.dp))

             //Categories List Component
            CategoriesList(
                categories = categories.value.map { it.name },
                selectedCategories = selectedCategories
            )

        }
    }
}






