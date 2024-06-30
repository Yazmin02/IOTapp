package com.example.iotapp.data.network

import com.example.iotapp.data.models.ApiResponse
import com.example.iotapp.data.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("authenticate_user")
    suspend fun authenticateUser(@Body user: User): Response<ApiResponse>
}
