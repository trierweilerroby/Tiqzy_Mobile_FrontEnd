package com.example.tiqzy_mobile_frontend.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.tiqzy_mobile_frontend.ui.components.EventItem
import com.example.tiqzy_mobile_frontend.viewmodel.FavoritesViewModel

@Composable
fun FavoritesScreen(navController: NavController, favoritesViewModel: FavoritesViewModel) {
    LazyColumn {
        items(favoritesViewModel.favorites) { event ->
            // You can reuse the EventItem composable here
            EventItem(event = event, favoritesViewModel = favoritesViewModel)
        }
    }
}
