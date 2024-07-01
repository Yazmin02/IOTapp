package com.example.iotapp.data.network

import com.example.iotapp.data.models.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("authenticate_user")
    suspend fun authenticateUser(@Body request: LoginRequest): Response<ResponseBody>

    @Headers("Content-Type: application/json")
    @POST("register_user")
    suspend fun registerUser(@Body request: RegisterRequest): Response<ResponseBody>

    @Headers("Content-Type: application/json")
    @POST("LocationFunctions")
    suspend fun manageLocation(@Body request: LocationRequest): Response<ResponseBody>

    @Headers("Content-Type: application/json")
    @POST("BeanFunctions")
    suspend fun manageJar(@Body request: JarRequest): Response<ResponseBody>

    @Headers("Content-Type: application/json")
    @POST("BeanFunctions")
    suspend fun getJarsByLocation(@Body request: JarRequest): Response<List<Jar>>

    @GET("event_history/{binId}/{timeRange}")
    suspend fun getEventHistory(
        @Path("binId") binId: Int,
        @Path("timeRange") timeRange: String
    ): Response<ResponseBody>
}
