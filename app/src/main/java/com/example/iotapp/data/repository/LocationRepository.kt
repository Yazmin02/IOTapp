package com.example.iotapp.data.repository

import com.example.iotapp.data.models.ApiResponse
import com.example.iotapp.data.models.Location
import com.example.iotapp.data.network.ApiClient
import retrofit2.Response

class LocationRepository {
    private val apiService = ApiClient.apiService

    suspend fun createLocation(name: String, details: String): Response<ApiResponse> {
        val request = mapOf("operation" to "create", "name" to name, "details" to details)
        return apiService.createLocation(request)
    }

    suspend fun getLocations(): Response<List<Location>> {
        val request = mapOf("operation" to "read")
        return apiService.getLocations(request)
    }

    suspend fun updateLocation(id: Int, name: String, details: String): Response<ApiResponse> {
        val request = mapOf("operation" to "update", "id" to id, "name" to name, "details" to details)
        return apiService.updateLocation(request)
    }

    suspend fun deleteLocation(id: Int): Response<ApiResponse> {
        val request = mapOf("operation" to "delete", "id" to id)
        return apiService.deleteLocation(request)
    }
}
