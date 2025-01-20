package com.example.tiqzy_mobile_frontend.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tiqzy_mobile_frontend.data.model.Ticket
import com.example.tiqzy_mobile_frontend.data.repository.OrderRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderRepository: OrderRepository
) : ViewModel() {

    private val _tickets = MutableStateFlow<List<Ticket>>(emptyList())
    val tickets: StateFlow<List<Ticket>> = _tickets

    fun fetchUserTickets(userId: String) {
        viewModelScope.launch {

            println("Current user ID: $userId")

            val userTickets = orderRepository.getUserTickets(userId)
            _tickets.value = userTickets
        }
    }

    fun addTicket(
        name: String,
        date: String,
        location: String,
        imageUrl: String,
        timeframe: String,
        userId: String,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        viewModelScope.launch {
            val result = orderRepository.addTicket(name, date, location, imageUrl, timeframe, userId)
            result.onSuccess { onSuccess() }
            result.onFailure { onError(it) }
        }
    }
}
