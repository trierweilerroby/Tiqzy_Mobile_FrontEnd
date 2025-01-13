package com.example.tiqzy_mobile_frontend.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.launch

class SignupViewModel(private val dataStore: DataStore<Preferences>) : ViewModel() {

    private val isLoggedKey = booleanPreferencesKey("is_logged")
    private val userNameKey = stringPreferencesKey("user_name")
    private val userEmailKey = stringPreferencesKey("user_email")
    private val userPhoneKey = stringPreferencesKey("user_phone")
    private val userPasswordKey = stringPreferencesKey("user_password")

    fun storeUserData(fullName: String, email: String, phoneNumber: String, password: String) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[userNameKey] = fullName
                preferences[userEmailKey] = email
                preferences[userPhoneKey] = phoneNumber
                preferences[userPasswordKey] = password
                preferences[isLoggedKey] = true
            }
        }
    }
}
