package com.example.tiqzy_mobile_frontend.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.tiqzy_mobile_frontend.data.database.DataStoreKeys
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FirebaseRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val dataStore: DataStore<Preferences>
) {

    // Login and store user
    suspend fun login(email: String, password: String): Result<String> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            storeLoggedInUser(email)
            Result.success("Login successful")

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Register and store user
    suspend fun register(email: String, password: String): Result<String> {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            storeLoggedInUser(email) // Store user after registration
            Result.success("Registration successful")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Logout and clear user data
    suspend fun logout() {
        FirebaseAuth.getInstance().signOut()
        clearLoggedInUser()
    }

    // Store logged-in user in DataStore
    private suspend fun storeLoggedInUser(email: String) {
        dataStore.edit { preferences ->
            preferences[DataStoreKeys.CURRENT_USER_EMAIL] = email
        }
    }

    // Clear logged-in user data
    private suspend fun clearLoggedInUser() {
        dataStore.edit { preferences ->
            preferences.remove(DataStoreKeys.CURRENT_USER_EMAIL)
        }
    }

    // Observe the logged-in user's email
    val currentUserEmail: Flow<String?> = dataStore.data.map { preferences ->
        preferences[DataStoreKeys.CURRENT_USER_EMAIL]
    }

    fun updateUserName(name: String, onComplete: (Boolean, String?) -> Unit) {
        val user = auth.currentUser

        user?.let {
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build()

            it.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onComplete(true, null) // Success
                    } else {
                        onComplete(false, task.exception?.message) // Error
                    }
                }
        } ?: onComplete(false, "User is not logged in")
    }

    fun getUserName(): String? {
        val user = auth.currentUser
        return user?.displayName
    }

}

