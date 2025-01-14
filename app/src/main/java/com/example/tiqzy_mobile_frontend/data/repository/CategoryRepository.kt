package com.example.tiqzy_mobile_frontend.data.repository

import com.example.tiqzy_mobile_frontend.data.model.Category
import com.example.tiqzy_mobile_frontend.data.network.CategoryApiService

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
                image = "https://www.uni-jena.de/unijenamedia/350955/header-2024.jpeg?height=428&width=760"
            ),
            Category(
                categoryId = 2,
                name = "Sports",
                image = "https://cuatro.sim-cdn.nl/opsterland/uploads/styles/medium_4x2_960x480/media/adobestock_190038155-1.jpg?h=3fbecb39&cb=2ysqNI_p",

            ),
            Category(
                categoryId = 3,
                name = "Food & Drink",
                image = "https://images.immediate.co.uk/production/volatile/sites/30/2022/06/Party-food-recipes-fcfb3af.jpg?quality=90&webp=true&resize=600,545",

            ),
            Category(
                categoryId = 4,
                name = "Children’s Events",
                image = "https://expatshaarlem.nl/wp-content/uploads/2018/12/pexels-photo-296301-845x321.jpeg",
            ),
            Category(
                categoryId = 5,
                name = "Science",
                image = "https://www.dni.gov/files/NCSC/images/homepage_images/Safeguarding.jpg",
            ),
            Category(
                categoryId = 6,
                name = "Literature",
                image = "https://nexus-education.com/wp-content/uploads/2024/06/role-of-literature-in-modern-education.jpeg",
            ),
            Category(
                categoryId = 7,
                name = "Art",
                image = "https://media.timeout.com/images/106006274/image.jpg",
            ),
            Category(
                categoryId = 8,
                name = "Technology",
                image = "https://www.simplilearn.com/ice9/free_resources_article_thumb/Technology_Trends.jpg",
            )
        )
    }

    // Temporary hardcoded category details
    fun fetchByCategoryId(categoryId: Int): Category? {
        return fetchCategories().find { it.categoryId == categoryId }
    }
}
