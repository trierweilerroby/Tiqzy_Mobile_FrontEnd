package com.example.tiqzy_mobile_frontend.data.repository

import com.example.tiqzy_mobile_frontend.data.network.AuthApiService
import com.example.tiqzy_mobile_frontend.data.network.LoginRequest
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApiService: AuthApiService
) {
    suspend fun login(email: String, password: String) =
        authApiService.login(LoginRequest(email, password))
}
