package com.example.iotapp.data.repository

import com.example.iotapp.data.models.User
import com.example.iotapp.data.network.ApiService
import okhttp3.ResponseBody
import retrofit2.Response

class UserRepository(private val apiService: ApiService) {
    suspend fun authenticateUser(user: User): Response<ResponseBody> {
        return apiService.authenticateUser(user)
    }
}
