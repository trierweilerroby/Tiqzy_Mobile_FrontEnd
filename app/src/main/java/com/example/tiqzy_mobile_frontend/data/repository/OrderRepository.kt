package com.example.tiqzy_mobile_frontend.data.repository

import com.example.tiqzy_mobile_frontend.data.model.Ticket
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    suspend fun getUserTickets(userId: String): List<Ticket> {
        println("Fetching tickets for userId: $userId")
        return try {
            val querySnapshot = firestore.collection("tickets")
                .whereEqualTo("userId", userId)
                .get()
                .await()
            println("Query succeeded: ${querySnapshot.documents.size} documents found")
            querySnapshot.documents.mapNotNull { doc ->
                println("Document: ${doc.data}")
                doc.toObject(Ticket::class.java)
            }
        } catch (e: Exception) {
            println("Failed to fetch tickets: ${e.message}")
            emptyList()
        }
    }


    suspend fun addTicket(
        name: String,
        date: String,
        location: String,
        imageUrl: String,
        timeframe: String,
        userId: String
    ): Result<Unit> {
        return try {
            val ticketData = mapOf(
                "date" to date,
                "location" to location,
                "name" to name,
                "imageUrl" to imageUrl,
                "timeframe" to timeframe,
                "userId" to userId
            )

            val documentRef = firestore.collection("tickets").document() // Generate unique ID
            ticketData.plus("id" to documentRef.id) // Add the generated ID to the ticket data

            documentRef.set(ticketData).await() // Save ticket to Firebase
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}
