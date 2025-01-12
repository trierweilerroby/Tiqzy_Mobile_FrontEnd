package com.example.tiqzy_mobile_frontend.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.launch
import androidx.datastore.preferences.core.stringPreferencesKey

class SignupViewModel(private val dataStore: DataStore<Preferences>) : ViewModel() {

    fun storeUserData(fullName: String, email: String, phoneNumber: String, password: String) {
        val userNameKey = stringPreferencesKey("user_name")
        val emailKey = stringPreferencesKey("user_email")
        val phoneKey = stringPreferencesKey("user_phone")
        val passwordKey = stringPreferencesKey("user_password")

        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[userNameKey] = fullName
                preferences[emailKey] = email
                preferences[phoneKey] = phoneNumber
                preferences[passwordKey] = password
            }
        }
    }
}
