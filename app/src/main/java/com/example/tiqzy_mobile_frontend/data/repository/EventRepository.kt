package com.example.tiqzy_mobile_frontend.data.repository

import com.example.tiqzy_mobile_frontend.data.model.Category
import com.example.tiqzy_mobile_frontend.data.model.Event
import com.example.tiqzy_mobile_frontend.data.model.Venue
import com.example.tiqzy_mobile_frontend.data.network.EventApiService
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val eventApiService: EventApiService
) {
    suspend fun fetchEvents(
        date: String? = null,
        venueCity: String? = null,
        categories: List<String>? = null,
        sort: String? = null
    ): List<Event> {
        return try {
            val categoriesQuery = categories?.joinToString(",")
            println("Fetching events from API with filters - Date: $date, VenueCity: $venueCity, Categories: $categoriesQuery, Sort: $sort")
            val events = eventApiService.getEvents(
                date = date,
                venueCity = venueCity,
                categories = categoriesQuery,
                sort = sort
            )
            println("API Response: $events")
            if (events.isNotEmpty()) events else getDefaultEvents()
        } catch (e: Exception) {
            e.printStackTrace()
            println("Fetching backup events due to API failure.")
            getDefaultEvents()
        }
    }

    suspend fun fetchEventById(eventId: Int): Event? {
        return try {
            println("Fetching event with ID: $eventId from API...")
            eventApiService.getEventById(id = eventId)
        } catch (e: retrofit2.HttpException) {
            if (e.code() == 404) {
                println("Event ID $eventId not found on API. Falling back to all events.")
                val allEvents = eventApiService.getEvents()
                allEvents.find { it.id == eventId }
            } else {
                throw e
            }
        }
    }

    suspend fun fetchSortedEvents(sortBy: String): List<Event> {
        return try {
            println("Fetching events sorted by: $sortBy")
            val events = eventApiService.getEvents(sort = sortBy)
            println("Fetched sorted events: $events")
            events
        } catch (e: Exception) {
            e.printStackTrace()
            println("Error fetching sorted events, returning empty list.")
            emptyList()
        }
    }


   suspend fun fetchEventsByCategory(categories: List<String>): List<Event> {
        return try {
            val categoriesQuery = categories.joinToString(",")
            println("Fetching events by categories: $categoriesQuery")
            eventApiService.getEvents(categories = categoriesQuery)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList() // Return an empty list in case of an error
        }
   }


    private fun fetchDefaultEventById(eventId: Int): Event {
        val defaultVenue = Venue(
            city = "Amsterdam",
            address = "Dam Square",
            country = "Netherlands",
            geoLat = 52.374,
            geoLng = 4.897,
            zip = "1012"
        )

        val defaultCategory = Category(
            categoryId = 1,
            name = "Default",
            image = "https://www.uni-jena.de/unijenamedia/350955/header-2024.jpeg?height=428&width=760"
        )

        // Returning a default event based on the event ID
        return Event(
            id = eventId,
            title = "Default Event $eventId",
            description = "This is a default event for demonstration purposes.",
            date = "2025-01-01",
            price = 5000 + (eventId * 1000), // Differentiating price based on ID
            venue = defaultVenue,
            imageUrl = "https://example.com/default.jpg",
            categories = listOf(defaultCategory)
        )
    }

    private fun getDefaultEvents(): List<Event> {
        return listOf(
            fetchDefaultEventById(1),
            fetchDefaultEventById(2)
        )
    }
}
