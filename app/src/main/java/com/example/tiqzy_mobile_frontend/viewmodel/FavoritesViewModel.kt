package com.example.tiqzy_mobile_frontend.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.tiqzy_mobile_frontend.data.model.Event

open class FavoritesViewModel : ViewModel() {
    private val _favorites = mutableStateListOf<Event>()
    open val favorites: List<Event> get() = _favorites

    open fun isFavorite(event: Event): Boolean {
        return _favorites.contains(event)
    }

    open fun toggleFavorite(event: Event) {
        println("toggle favorite")
        if (_favorites.contains(event)) {
            println("favorite removing")
            _favorites.remove(event)
        } else {
            println("favorite adding")
            _favorites.add(event)
        }
    }
}
