package com.example.tiqzy_mobile_frontend.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tiqzy_mobile_frontend.data.database.DataStoreSingleton
import com.example.tiqzy_mobile_frontend.data.model.Event
import com.example.tiqzy_mobile_frontend.data.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val dataStoreSingleton: DataStoreSingleton
) : ViewModel() {

    private val _favorites = MutableStateFlow<Set<String>>(emptySet())
    val favorites: StateFlow<Set<String>> = _favorites

    init {
        viewModelScope.launch {
            dataStoreSingleton.favorites.collect { favoriteIds ->
                _favorites.value = favoriteIds
            }
        }
    }

    fun toggleFavorite(event: Event) {
        viewModelScope.launch {
            val eventId = event.id.toString()
            if (_favorites.value.contains(eventId)) {
                dataStoreSingleton.removeFavorite(eventId)
            } else {
                dataStoreSingleton.addFavorite(eventId)
            }
        }
    }

    fun isFavorite(event: Event): Boolean {
        return _favorites.value.contains(event.id.toString())
    }

    fun mapFavoriteIdsToEvents(favoriteIds: Set<String>, allEvents: List<Event>): List<Event> {
        return allEvents.filter { event -> favoriteIds.contains(event.id.toString()) }
    }
}
