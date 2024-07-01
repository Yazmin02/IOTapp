package com.example.iotapp.data.repository

import com.example.iotapp.data.models.LoginRequest
import com.example.iotapp.data.models.RegisterRequest
import com.example.iotapp.data.network.ApiService
import okhttp3.ResponseBody
import retrofit2.Response

// Repositorio para manejar las operaciones relacionadas con los usuarios
class UserRepository(private val apiService: ApiService) {
    // Función para autenticar a un usuario
    suspend fun authenticateUser(request: LoginRequest): Response<ResponseBody> {
        return apiService.authenticateUser(request)
    }

    // Función para registrar a un nuevo usuario
    suspend fun registerUser(request: RegisterRequest): Response<ResponseBody> {
        return apiService.registerUser(request)
    }
}
