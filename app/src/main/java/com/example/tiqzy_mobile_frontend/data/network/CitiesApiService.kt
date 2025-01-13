package com.example.tiqzy_mobile_frontend.data.network


import com.example.tiqzy_mobile_frontend.data.model.Category
import com.example.tiqzy_mobile_frontend.data.model.City
import retrofit2.http.GET
import retrofit2.http.Path

interface CitiesApiService {
    @GET("Cities")
    suspend fun getCities(): List<City>

}