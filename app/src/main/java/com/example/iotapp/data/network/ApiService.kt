package com.example.iotapp.data.network

import com.example.iotapp.data.models.ApiResponse
import com.example.iotapp.data.models.Location
import com.example.iotapp.data.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("authenticate_user")
    suspend fun authenticateUser(@Body user: User): Response<ApiResponse>

    @POST("LocationFunctions/create")
    suspend fun createLocation(@Body request: Map<String, String>): Response<ApiResponse>

    @POST("LocationFunctions/get")
    suspend fun getLocations(@Body request: Map<String, String>): Response<List<Location>>

    @POST("LocationFunctions/update")
    suspend fun updateLocation(@Body request: Map<String, Any>): Response<ApiResponse>

    @POST("LocationFunctions/delete")
    suspend fun deleteLocation(@Body request: Map<String, Any>): Response<ApiResponse>
}
