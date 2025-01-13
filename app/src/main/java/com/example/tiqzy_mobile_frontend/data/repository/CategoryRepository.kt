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
            ),
            Category(
                categoryId = 4,
                name = "Children’s Events",
                image = "https://expatshaarlem.nl/wp-content/uploads/2018/12/pexels-photo-296301-845x321.jpeg",
                event = listOf(
                    Event(eventId = 7, eventName = "Painting Exhibition"),
                    Event(eventId = 8, eventName = "Sculpture Workshop")
                )
            ),
            Category(
                categoryId = 5,
                name = "Science",
                image = "https://www.dni.gov/files/NCSC/images/homepage_images/Safeguarding.jpg",
                event = listOf(
                    Event(eventId = 9, eventName = "Tech Conference"),
                    Event(eventId = 10, eventName = "Hackathon")
                )
            ),
            Category(
                categoryId = 6,
                name = "Literature",
                image = "https://nexus-education.com/wp-content/uploads/2024/06/role-of-literature-in-modern-education.jpeg",
                event = listOf(
                    Event(eventId = 11, eventName = "Drama Play"),
                    Event(eventId = 12, eventName = "Stand-up Comedy")
                )
            ),
            Category(
                categoryId = 7,
                name = "Art",
                image = "https://media.timeout.com/images/106006274/image.jpg",
                event = listOf(
                    Event(eventId = 13, eventName = "Science Fair"),
                    Event(eventId = 14, eventName = "Astronomy Night")
                )
            ),
            Category(
                categoryId = 8,
                name = "Technology",
                image = "https://www.simplilearn.com/ice9/free_resources_article_thumb/Technology_Trends.jpg",
                event = listOf(
                    Event(eventId = 15, eventName = "Travel Expo"),
                    Event(eventId = 16, eventName = "Cultural Exchange")
                )
            )
        )
    }

    // Temporary hardcoded category details
    fun fetchByCategoryId(categoryId: Int): Category? {
        return fetchCategories().find { it.categoryId == categoryId }
    }
}
