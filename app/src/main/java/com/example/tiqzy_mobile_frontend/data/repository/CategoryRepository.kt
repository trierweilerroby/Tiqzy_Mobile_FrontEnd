package com.example.tiqzy_mobile_frontend.data.repository

import com.example.tiqzy_mobile_frontend.data.network.CategoryApiService
import com.example.tiqzy_mobile_frontend.ui.components.Category
import com.example.tiqzy_mobile_frontend.ui.components.Event
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    // this will be used when the backend is ready
    //private val categoryApiService: CategoryApiService
) {


    // TODO: Replace hardcoded categories with API call using categoryApiService
    fun fetchCategories(): List<Category> {
        return listOf(
            Category(
                categoryId = 1,
                name = "Music",
                image = "https://example.com/music.jpg",
                event = listOf(
                    Event(eventId = 1, eventName = "Concert"),
                    Event(eventId = 2, eventName = "Festival")
                )
            ),
            Category(
                categoryId = 2,
                name = "Sports",
                image = "https://example.com/sports.jpg",
                event = listOf(
                    Event(eventId = 3, eventName = "Soccer Match"),
                    Event(eventId = 4, eventName = "Marathon")
                )
            ),
            Category(
                categoryId = 3,
                name = "Food",
                image = "https://example.com/food.jpg",
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
