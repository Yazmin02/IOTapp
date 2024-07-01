package com.example.iotapp.data.repository

import com.example.iotapp.data.models.Jar
import com.example.iotapp.data.models.JarRequest
import com.example.iotapp.data.network.ApiService
import okhttp3.ResponseBody
import retrofit2.Response

class JarRepository(private val apiService: ApiService) {

    suspend fun createJar(jar: Jar): Response<ResponseBody> {
        val request = JarRequest(
            operation = "create",
            locationId = jar.locationId,
            description = jar.description
        )
        return apiService.manageJar(request)
    }

    suspend fun readJars(): Response<ResponseBody> {
        val request = JarRequest(operation = "read")
        return apiService.manageJar(request)
    }

    suspend fun updateJar(jar: Jar): Response<ResponseBody> {
        val request = JarRequest(
            operation = "update",
            id = jar.id,
            locationId = jar.locationId,
            description = jar.description
        )
        return apiService.manageJar(request)
    }

    suspend fun deleteJar(id: Int): Response<ResponseBody> {
        val request = JarRequest(
            operation = "delete",
            id = id
        )
        return apiService.manageJar(request)
    }

    suspend fun getJarsByLocation(locationId: Int): Response<List<Jar>> {
        val request = JarRequest(
            operation = "getByLocation",
            locationId = locationId
        )
        return apiService.getJarsByLocation(request)
    }
}
