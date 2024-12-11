package com.example.tiqzy_mobile_frontend.data.network

import com.example.tiqzy_mobile_frontend.data.model.Category
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryApiService {
    @GET("Categories")
    suspend fun getCategories(): List<Category>

    @GET("Categories/{id}")
    suspend fun getCategoryById(@Path("id") id: Int): Category
}