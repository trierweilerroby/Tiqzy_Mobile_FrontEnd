package com.example.tiqzy_mobile_frontend.data.repository

import com.example.tiqzy_mobile_frontend.data.network.EventApiService
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val apiService: EventApiService
) {
    suspend fun fetchEvents() = apiService.getEvents()
}