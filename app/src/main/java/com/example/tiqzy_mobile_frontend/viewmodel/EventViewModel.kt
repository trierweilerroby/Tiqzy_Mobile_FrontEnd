package com.example.tiqzy_mobile_frontend.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tiqzy_mobile_frontend.data.model.Category
import com.example.tiqzy_mobile_frontend.data.model.City
import com.example.tiqzy_mobile_frontend.data.model.Event
import com.example.tiqzy_mobile_frontend.data.network.EventApiService
import com.example.tiqzy_mobile_frontend.data.repository.CategoryRepository
import com.example.tiqzy_mobile_frontend.data.repository.CitiesRepository
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
    private val eventApiService: EventApiService,
    private val citiesRepository: CitiesRepository
) : ViewModel() {
    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events

    // Flow for a single event
    private val _selectedEvent = MutableStateFlow<Event?>(null)
    val selectedEvent: StateFlow<Event?> = _selectedEvent

    private val _sortKey = MutableStateFlow("")
    val sortKey: StateFlow<String> get() = _sortKey

    private val _isSortPopupVisible = MutableStateFlow(false)
    val isSortPopupVisible: Boolean get() = _isSortPopupVisible.value

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    private val _cities = MutableStateFlow<List<City>>(emptyList())
    val cities: StateFlow<List<City>> = _cities

    init {
        fetchEvents()
        fetchCities()
    }

    fun fetchEvents(
        date: String? = null,
        venueCity: String? = null,
        categories: List<String>? = null,
        sort: String? = null
    ) {
        viewModelScope.launch {
            println("Fetching events for city: $venueCity")
            try {
                val events = repository.fetchEvents(
                    date = date,
                    venueCity = venueCity,
                    categories = categories,
                    sort = sort
                )
                _events.value = events
            } catch (e: Exception) {
                println("Error fetching events: ${e.message}")
                _events.value = emptyList()
            }
        }
    }



    fun fetchEventById(eventId: Int) {
        viewModelScope.launch {
            println("fetchEventById called with eventId: $eventId")
            try {
                val event = repository.fetchEventById(eventId)
                _selectedEvent.value = event
                println("Selected Event: $event")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchEventsFilteredByCategories(categories: String) {
        viewModelScope.launch {
            try {
                val response = eventApiService.getEvents(categories = categories)
                _events.value = response
            } catch (e: Exception) {
                println("Error fetching filtered events: ${e.message}")
            }
        }
    }

    fun fetchEventsSortedBy(sortKey: String) {
        viewModelScope.launch {
            try {
                val sortedEvents = repository.fetchSortedEvents(sortBy = sortKey)
                _events.value = sortedEvents
                println("Events sorted by $sortKey: ${sortedEvents.size} events found")
            } catch (e: Exception) {
                println("Error fetching sorted events: ${e.message}")
            }
        }
    }

    fun updateSortKey(newSortKey: String) {
        _sortKey.value = newSortKey
    }

       // Function to fetch filtered events
       fun fetchFilteredEvents(location: String? = null, date: String? = null) {
           viewModelScope.launch {
               try {
                   val response = eventApiService.getEvents(
                       venueCity = location.takeIf { !it.isNullOrEmpty() },
                       date = date.takeIf { !it.isNullOrEmpty() }
                   )
                   _events.value = response
               } catch (e: Exception) {
                   println("Error fetching filtered events: ${e.message}")
                   _events.value = emptyList()
               }
           }
       }


    fun fetchCities() {
        viewModelScope.launch {
            _cities.value = citiesRepository.fetchCities()
        }
    }

}
