package com.example.iotapp.data.repository

import com.example.iotapp.data.models.ApiResponse
import com.example.iotapp.data.models.User
import com.example.iotapp.data.network.ApiClient
import retrofit2.Response

class UserRepository {
    private val apiService = ApiClient.apiService

    suspend fun authenticateUser(user: User): Response<ApiResponse> {
        return apiService.authenticateUser(user)
    }

    // Otros métodos según sea necesario para interactuar con la API
}
