package com.example.tiqzy_mobile_frontend.data.model


data class Event(
    val id: Int,
    val title: String,
    val description: String,
    val date: String,
    val price: Int, // In cents, convert to Euros in the UI if needed
    val image: Image?, // Corresponds to `image_url` in the API
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


data class Image(
    val url: String?,
    val id: Int?,
    val extension: String?,
    val width: Int?,
    val height: Int?,
    val filesize: Int?,
    val sizes: Sizes? // Nested object for sizes
)

data class Sizes(
    val medium: ImageSize?,
    val thumbnail: ImageSize?,
    val woocommerce_thumbnail: ImageSize?,
    val woocommerce_gallery_thumbnail: ImageSize?
)

data class ImageSize(
    val width: Int?,
    val height: Int?,
    val mimeType: String?, // Changed to camelCase to match Kotlin conventions
    val filesize: Int?,
    val url: String?,
    val uncropped: Boolean? = null // Optional as not all sizes have this field
)



