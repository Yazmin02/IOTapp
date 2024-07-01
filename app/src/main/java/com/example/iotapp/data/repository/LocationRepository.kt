package com.example.iotapp.data.repository

import com.example.iotapp.data.models.Location
import com.example.iotapp.data.models.LocationRequest
import com.example.iotapp.data.network.ApiService
import okhttp3.ResponseBody
import retrofit2.Response

class LocationRepository(private val apiService: ApiService) {

    suspend fun createLocation(location: Location): Response<ResponseBody> {
        val request = LocationRequest(
            operation = "create",
            name = location.name,
            details = location.details
        )
        return apiService.manageLocation(request)
    }

    suspend fun readLocations(): Response<ResponseBody> {
        val request = LocationRequest(operation = "read")
        return apiService.manageLocation(request)
    }

    suspend fun updateLocation(location: Location): Response<ResponseBody> {
        val request = LocationRequest(
            operation = "update",
            id = location.id,
            name = location.name,
            details = location.details
        )
        return apiService.manageLocation(request)
    }

    suspend fun deleteLocation(id: Int): Response<ResponseBody> {
        val request = LocationRequest(
            operation = "delete",
            id = id
        )
        return apiService.manageLocation(request)
    }
}
