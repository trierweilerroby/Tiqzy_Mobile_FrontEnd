package com.example.tiqzy_mobile_frontend.data.database

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// Extension for accessing DataStore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

object DataStoreKeys {
    val FAVORITES_KEY = stringSetPreferencesKey("favorites")
    val IS_LOGGED_IN_KEY = booleanPreferencesKey("is_logged_in")
}

class DataStoreSingleton @Inject constructor(private val context: Context) {

    private val dataStore = context.dataStore

    val favorites: Flow<Set<String>> = dataStore.data.map { preferences ->
        preferences[DataStoreKeys.FAVORITES_KEY] ?: emptySet()
    }

    suspend fun addFavorite(eventId: String) {
        dataStore.edit { preferences ->
            val currentFavorites = preferences[DataStoreKeys.FAVORITES_KEY] ?: emptySet()
            preferences[DataStoreKeys.FAVORITES_KEY] = currentFavorites + eventId
        }
    }

    suspend fun removeFavorite(eventId: String) {
        dataStore.edit { preferences ->
            val currentFavorites = preferences[DataStoreKeys.FAVORITES_KEY] ?: emptySet()
            preferences[DataStoreKeys.FAVORITES_KEY] = currentFavorites - eventId
        }
    }


    //login

    // Get login state
    val isLoggedIn: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[DataStoreKeys.IS_LOGGED_IN_KEY] ?: false
    }

    // Set login state
    suspend fun setLoggedIn(isLoggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[DataStoreKeys.IS_LOGGED_IN_KEY] = isLoggedIn
        }
    }

    // Clear all data
    suspend fun clearDataStore() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}

