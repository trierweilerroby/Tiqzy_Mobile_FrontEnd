package com.example.tiqzy_mobile_frontend.data.network

import com.example.tiqzy_mobile_frontend.data.model.Event
import retrofit2.http.GET

interface EventApiService {
    @GET("Events") // Replace with your actual endpoint
    suspend fun getEvents(): List<Event>
}