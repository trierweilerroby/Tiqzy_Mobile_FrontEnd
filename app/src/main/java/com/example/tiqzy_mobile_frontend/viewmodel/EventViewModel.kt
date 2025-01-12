package com.example.tiqzy_mobile_frontend.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tiqzy_mobile_frontend.ui.components.Category
import com.example.tiqzy_mobile_frontend.data.model.Event
import com.example.tiqzy_mobile_frontend.data.network.EventApiService
import com.example.tiqzy_mobile_frontend.data.repository.CategoryRepository
import com.example.tiqzy_mobile_frontend.data.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val repository: EventRepository,
    private val categoryRepository: CategoryRepository,
    private val eventRepository: EventRepository
) : ViewModel() {
    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events

    // Flow for a single event
    private val _selectedEvent = MutableStateFlow<Event?>(null)
    val selectedEvent: StateFlow<Event?> = _selectedEvent

    private val _sortedEvents = MutableStateFlow<List<Event>>(emptyList())
    val sortedEvents: StateFlow<List<Event>> = _sortedEvents

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    init {
        fetchEvents()
    }

    fun fetchEvents(date: String? = null, venueCity: String? = null) {
        viewModelScope.launch {
            try {
                val fetchedEvents = repository.fetchEvents(date = date, venueCity = venueCity)
                println("Fetched events: $fetchedEvents") // Log events
                _events.value = fetchedEvents
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchEventById(eventId: Int) {
        viewModelScope.launch {
            println("fetchEventById called with eventId: $eventId") // Debug log
            try {
                val event = repository.fetchEventById(eventId)
                _selectedEvent.value = event
                println("Selected Event: $event") // Add this line
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchEventsByCategory(categories: List<String>) {
        viewModelScope.launch {
            try {
                val events = repository.fetchEventsByCategory(categories)
                _events.value = events
                println("Fetched events by category: $events")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchEventsSortedBy(sortKey: String) {
        viewModelScope.launch {
            try {
                val events = repository.fetchSortedEvents(sortKey)
                _sortedEvents.value = events
                println("Sorted events fetched: $events")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchCategories() {
        viewModelScope.launch {
            // Fetch categories from CategoryRepository
            _categories.value = categoryRepository.fetchCategories()
        }
    }

}
