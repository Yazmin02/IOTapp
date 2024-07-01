package com.example.iotapp.data.network

import com.example.iotapp.data.models.LoginRequest
import com.example.iotapp.data.models.RegisterRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Path

// Interfaz para definir los endpoints de la API
interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("authenticate_user")
    suspend fun authenticateUser(@Body request: LoginRequest): Response<ResponseBody>

    @Headers("Content-Type: application/json")
    @POST("register_user")
    suspend fun registerUser(@Body request: RegisterRequest): Response<ResponseBody>

    @Headers("Content-Type: application/json")
    @POST("LocationFunctions")
    suspend fun manageLocation(@Body request: Map<String, Any>): Response<ResponseBody>

    @Headers("Content-Type: application/json")
    @POST("JarFunctions")
    suspend fun manageJar(@Body request: Map<String, Any>): Response<ResponseBody>

    @GET("event_history/{binId}/{timeRange}")
    suspend fun getEventHistory(
        @Path("binId") binId: Int,
        @Path("timeRange") timeRange: String
    ): Response<ResponseBody>
}
