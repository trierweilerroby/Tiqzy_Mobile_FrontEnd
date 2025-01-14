package com.example.tiqzy_mobile_frontend.data.repository

import com.example.tiqzy_mobile_frontend.data.database.DataStoreSingleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class FavoritesRepository @Inject constructor(
    private val dataStoreSingleton: DataStoreSingleton
) {

    val favorites: Flow<Set<String>> = dataStoreSingleton.favorites

    suspend fun toggleFavorite(eventId: String) {
        val currentFavorites = favorites.first()
        if (currentFavorites.contains(eventId)) {
            dataStoreSingleton.removeFavorite(eventId)
        } else {
            dataStoreSingleton.addFavorite(eventId)
        }
    }
}
