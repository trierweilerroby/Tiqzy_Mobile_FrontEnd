package com.example.tiqzy_mobile_frontend.data.model

import android.media.Image

data class Category (
    val categoryId: Int,
    val name: String,
    val image: String,
    val event: List<Event>
)