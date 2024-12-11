package com.example.tiqzy_mobile_frontend.data.repository

import com.example.tiqzy_mobile_frontend.data.network.CategoryApiService
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val eventApiService: CategoryApiService
) {
    suspend fun fetchCategories() = eventApiService.getCategories()

    suspend fun fetchByCategoryId(categoryId: Int) = eventApiService.getCategoryById(categoryId)
}