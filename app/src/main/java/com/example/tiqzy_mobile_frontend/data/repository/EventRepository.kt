package com.example.tiqzy_mobile_frontend.data.repository

import com.example.tiqzy_mobile_frontend.data.network.EventApiService
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val eventApiService: EventApiService
) {
    suspend fun fetchEvents() = eventApiService.getEvents()

    suspend fun fetchEventsById(eventId: Int) = eventApiService.getEventsById(eventId)
}