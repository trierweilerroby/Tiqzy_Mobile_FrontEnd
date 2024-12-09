package com.example.tiqzy_mobile_frontend.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.tiqzy_mobile_frontend.data.model.Event

open class FavoritesViewModel : ViewModel() {
    private val _favorites = mutableStateListOf<Event>()
    open val favorites: List<Event> get() = _favorites

    // Toggle favorite state for an event
    open fun toggleFavorite(event: Event) {
        if (_favorites.contains(event)) {
            _favorites.remove(event)
        } else {
            _favorites.add(event)
        }
    }

    // Check if an event is in favorites
    open fun isFavorite(event: Event): Boolean {
        return _favorites.contains(event)
    }
}
