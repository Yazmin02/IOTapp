package com.example.iotapp.data.repository

import com.example.iotapp.data.models.Location
import com.example.iotapp.data.network.ApiService
import okhttp3.ResponseBody
import retrofit2.Response

class LocationRepository(private val apiService: ApiService) {
    suspend fun createLocation(location: Location): Response<ResponseBody> {
        val request = mapOf(
            "operation" to "create",
            "name" to location.name,
            "details" to location.details
        )
        return apiService.manageLocation(request)
    }

    suspend fun readLocations(): Response<ResponseBody> {
        val request = mapOf("operation" to "read")
        return apiService.manageLocation(request)
    }

    suspend fun updateLocation(location: Location): Response<ResponseBody> {
        val request = mapOf(
            "operation" to "update",
            "id" to location.id!!,
            "name" to location.name,
            "details" to location.details
        )
        return apiService.manageLocation(request)
    }

    suspend fun deleteLocation(id: Int): Response<ResponseBody> {
        val request = mapOf(
            "operation" to "delete",
            "id" to id
        )
        return apiService.manageLocation(request)
    }
}
