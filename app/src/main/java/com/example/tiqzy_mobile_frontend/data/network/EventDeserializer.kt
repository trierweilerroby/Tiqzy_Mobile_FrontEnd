package com.example.tiqzy_mobile_frontend.data.network

import com.example.tiqzy_mobile_frontend.data.model.Event
import com.example.tiqzy_mobile_frontend.data.model.Venue
import com.google.gson.*
import java.lang.reflect.Type

class EventDeserializer : JsonDeserializer<Event> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Event {
        val jsonObject = json.asJsonObject

        // Extract standard fields
        val id = jsonObject.get("id").asInt
        val title = jsonObject.get("title").asString
        val description = jsonObject.get("description").asString
        val date = jsonObject.get("date").asString
        val price = jsonObject.get("price").asInt

        // Handle venue field
        val venueElement = jsonObject.get("venue")
        val venue = context.deserialize<Venue>(venueElement, Venue::class.java)

        // Handle image field
        val imageElement = jsonObject.get("image")
        val imageUrl = if (imageElement.isJsonObject) {
            imageElement.asJsonObject.get("url").asString
        } else {
            null // Handle cases where `image` is `false`
        }

        return Event(
            id = id,
            title = title,
            description = description,
            date = date,
            price = price,
            venue = venue,
            imageUrl = imageUrl // Use the updated imageUrl field
        )
    }
}
