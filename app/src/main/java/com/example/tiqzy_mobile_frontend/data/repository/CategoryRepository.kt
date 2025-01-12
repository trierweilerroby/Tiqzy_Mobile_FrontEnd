package com.example.tiqzy_mobile_frontend.data.repository

import com.example.tiqzy_mobile_frontend.data.network.CategoryApiService
import com.example.tiqzy_mobile_frontend.ui.components.Category
import com.example.tiqzy_mobile_frontend.ui.components.Event
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    // this will be used when the backend is ready
    //private val categoryApiService: CategoryApiService
) {


    fun fetchCategories(): List<Category> {
        return listOf(
            Category(
                categoryId = 1,
                name = "Music",
                image = "https://www.uni-jena.de/unijenamedia/350955/header-2024.jpeg?height=428&width=760",
                event = listOf(
                    Event(eventId = 1, eventName = "Concert"),
                    Event(eventId = 2, eventName = "Festival")
                )
            ),
            Category(
                categoryId = 2,
                name = "Sports",
                image = "https://cuatro.sim-cdn.nl/opsterland/uploads/styles/medium_4x2_960x480/media/adobestock_190038155-1.jpg?h=3fbecb39&cb=2ysqNI_p",
                event = listOf(
                    Event(eventId = 3, eventName = "Soccer Match"),
                    Event(eventId = 4, eventName = "Marathon")
                )
            ),
            Category(
                categoryId = 3,
                name = "Food & Drink",
                image = "https://images.immediate.co.uk/production/volatile/sites/30/2022/06/Party-food-recipes-fcfb3af.jpg?quality=90&webp=true&resize=600,545",
                event = listOf(
                    Event(eventId = 5, eventName = "Food Expo"),
                    Event(eventId = 6, eventName = "Cooking Class")
                )
            )
        )
    }

    // Temporary hardcoded category details
    fun fetchByCategoryId(categoryId: Int): Category? {
        return fetchCategories().find { it.categoryId == categoryId }
    }
}
