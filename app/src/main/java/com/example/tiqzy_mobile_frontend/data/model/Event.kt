package com.example.tiqzy_mobile_frontend.data.model


data class Event(
    val id: Int,
    val title: String,
    val description: String,
    val date: String,
    val price: Int, // In cents, convert to Euros in the UI if needed
    val imageUrl: String?, // Corresponds to `image_url` in the API
    val venue: Venue?,
    val categories: List<Category> = emptyList()
)

data class Venue(
    val city: String,
    val address: String,
    val country: String,
    val geoLat: Double,
    val geoLng: Double,
    val zip: String
)





