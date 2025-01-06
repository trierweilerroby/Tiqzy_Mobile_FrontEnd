package com.example.tiqzy_mobile_frontend.data.network

import com.example.tiqzy_mobile_frontend.data.model.Event
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EventApiService {
    @GET("tickets/tickets")
    suspend fun getEvents(
        @Query("date") date: String? = null,
        @Query("venueCity") venueCity: String? = null
    ): List<Event>

    @GET("tickets/tickets/{id}")
    suspend fun getEventById(@Query("id") id: Int): Event
}
