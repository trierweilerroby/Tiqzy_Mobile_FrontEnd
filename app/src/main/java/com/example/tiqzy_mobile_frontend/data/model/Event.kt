package com.example.tiqzy_mobile_frontend.data.model

import android.location.Location

data class Event (
    val id: Int,
    val title: String,
    val description: String,
    val date: String,
    val price: Int,
    val location: String,
    val imageUrl: String
)