package com.example.tiqzy_mobile_frontend.data.repository

import com.example.tiqzy_mobile_frontend.data.model.Category
import com.example.tiqzy_mobile_frontend.data.model.CategoryUrls
import com.example.tiqzy_mobile_frontend.data.model.Event
import com.example.tiqzy_mobile_frontend.data.model.Image
import com.example.tiqzy_mobile_frontend.data.model.ImageSize
import com.example.tiqzy_mobile_frontend.data.model.Sizes
import com.example.tiqzy_mobile_frontend.data.model.Venue
import com.example.tiqzy_mobile_frontend.data.network.EventApiService
import javax.inject.Inject

 class EventRepository @Inject constructor(
    private val eventApiService: EventApiService
) {
    suspend fun fetchEvents(date: String? = null, venueCity: String? = null): List<Event> {
        return try {
            println("Fetching events from API...")
            val events = eventApiService.getEvents(date = date, venueCity = venueCity)
            println("API Response: $events")
            if (events.isNotEmpty()) events else getDefaultEvents()
        } catch (e: Exception) {
            e.printStackTrace()
            println("Fetching backup events due to API failure.")
            getDefaultEvents()
        }
    }

    suspend fun fetchEventById(eventId: Int): Event {
        return try {
            println("Fetching event with ID: $eventId from API...")
            eventApiService.getEventById(id = eventId)
        } catch (e: Exception) {
            e.printStackTrace()
            println("Fetching default event for ID: $eventId due to API failure.")
            fetchDefaultEventById(eventId)
        }
    }

    private suspend fun fetchDefaultEventById(eventId: Int): Event {
        val defaultVenue = Venue(
            city = "Amsterdam",
            address = "Dam Square",
            country = "Netherlands",
            geoLat = 52.374,
            geoLng = 4.897,
            zip = "1012"
        )

        val defaultImage = Image(
            url = "https://example.com/default.jpg",
            id = 1,
            extension = "jpg",
            width = 400,
            height = 400,
            filesize = 12345,
            sizes = Sizes(
                medium = ImageSize(300, 300, "image/jpeg", 12345, "https://example.com/default-medium.jpg"),
                thumbnail = ImageSize(150, 150, "image/jpeg", 12345, "https://example.com/default-thumb.jpg"),
                woocommerce_thumbnail = null,
                woocommerce_gallery_thumbnail = null
            )
        )

        val defaultCategory = Category(
            name = "Default Category",
            slug = "default-category",
            termGroup = 0,
            termTaxonomyId = 1,
            taxonomy = "default_taxonomy",
            description = "A default category for demonstration purposes.",
            parent = 0,
            count = 0,
            filter = "raw",
            id = 1,
            urls = CategoryUrls(
                self = "https://example.com/categories/default-category",
                collection = "https://example.com/categories"
            )
        )

        // Returning a default event based on the event ID
        return Event(
            id = eventId,
            title = "Default Event $eventId",
            description = "This is a default event for demonstration purposes.",
            date = "2025-01-01",
            price = 5000 + (eventId * 1000), // Differentiating price based on ID
            venue = defaultVenue,
            image = defaultImage,
            categories = listOf(defaultCategory)
        )
    }

    private suspend fun getDefaultEvents(): List<Event> {
        return listOf(
            fetchDefaultEventById(1),
            fetchDefaultEventById(2)
        )
    }
}
