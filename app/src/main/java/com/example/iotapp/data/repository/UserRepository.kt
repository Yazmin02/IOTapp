package com.example.iotapp.data.repository

import com.example.iotapp.data.models.User
import com.example.iotapp.data.network.ApiService
import okhttp3.ResponseBody
import retrofit2.Response

// Repositorio para manejar las operaciones relacionadas con los usuarios
class UserRepository(private val apiService: ApiService) {
    // Función para autenticar a un usuario
    suspend fun authenticateUser(user: User): Response<ResponseBody> {
        return apiService.authenticateUser(user)
    }

    // Función para registrar a un nuevo usuario
    suspend fun registerUser(user: User): Response<ResponseBody> {
        return apiService.registerUser(user)
    }
}
