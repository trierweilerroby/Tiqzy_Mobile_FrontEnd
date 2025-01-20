package com.example.tiqzy_mobile_frontend.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tiqzy_mobile_frontend.data.database.DataStoreSingleton
import com.example.tiqzy_mobile_frontend.data.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val dataStoreSingleton: DataStoreSingleton
) : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Observe the current user's email
    val currentUserEmail: StateFlow<String?> = firebaseRepository.currentUserEmail
        .map { it }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun login(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val result = firebaseRepository.login(email, password)
            result.fold(
                onSuccess = { message ->
                    val user = FirebaseAuth.getInstance().currentUser
                    val userName = user?.displayName ?: "User"
                    dataStoreSingleton.setUserName(userName)
                    dataStoreSingleton.setLoggedIn(true)
                    println("setting log state ")
                    onSuccess()
                },
                onFailure = { error ->
                    onError(error.message ?: "Login failed")
                }
            )
        }
    }


    fun register(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            val result = firebaseRepository.register(email, password)
            result.onSuccess { onSuccess() }
            result.onFailure { onError(it.message ?: "Unknown error") }
        }
    }

    fun logout(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                firebaseRepository.logout()
                dataStoreSingleton.setLoggedIn(false)
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Logout failed")
            }
        }
    }


    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun updateUserName(name: String, onComplete: (Boolean, String?) -> Unit) {
        firebaseRepository.updateUserName(name, onComplete)
    }

    fun getUserName(): String? {
        return firebaseRepository.getUserName()
    }
    fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }
}
