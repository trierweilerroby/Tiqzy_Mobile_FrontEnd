package com.example.tiqzy_mobile_frontend.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tiqzy_mobile_frontend.data.model.Event
import com.example.tiqzy_mobile_frontend.data.repository.EventRepository
import com.example.tiqzy_mobile_frontend.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val repository: EventRepository
) : ViewModel() {
    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events

    init {
        fetchEvents()
    }

    private fun fetchEvents() {
        viewModelScope.launch {
            try {
                _events.value = repository.fetchEvents()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}
