package com.example.iotapp.data.network

import com.example.iotapp.data.models.User
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("authenticate_user")
    suspend fun authenticateUser(@Body user: User): Response<ResponseBody>

    @Headers("Content-Type: application/json")
    @POST("register_user")
    suspend fun registerUser(@Body user: User): Response<ResponseBody>

    @Headers("Content-Type: application/json")
    @POST("LocationFunctions")
    suspend fun manageLocation(@Body request: Map<String, Any>): Response<ResponseBody>
}
