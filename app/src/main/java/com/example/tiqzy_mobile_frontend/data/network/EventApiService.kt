package com.example.tiqzy_mobile_frontend.data.network


import com.example.tiqzy_mobile_frontend.data.model.Event
import retrofit2.http.GET
import retrofit2.http.Path

interface EventApiService {
    @GET("Events")
    suspend fun getEvents(): List<Event>

    @GET("Events/{id}")
    suspend fun getEventsById(@Path("id") id: Int): Event
}