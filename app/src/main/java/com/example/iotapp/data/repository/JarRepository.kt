package com.example.iotapp.data.repository

import com.example.iotapp.data.models.Jar
import com.example.iotapp.data.network.ApiService
import okhttp3.ResponseBody
import retrofit2.Response

class JarRepository(private val apiService: ApiService) {

    suspend fun createJar(jar: Jar): Response<ResponseBody> {
        val request = mapOf(
            "operation" to "create",
            "locationId" to jar.locationId,
            "description" to jar.description
        )
        return apiService.manageJar(request)
    }

    suspend fun readJars(): Response<ResponseBody> {
        val request = mapOf("operation" to "read")
        return apiService.manageJar(request)
    }

    suspend fun updateJar(jar: Jar): Response<ResponseBody> {
        val request = mapOf(
            "operation" to "update",
            "id" to jar.id!!,
            "locationId" to jar.locationId,
            "description" to jar.description
        )
        return apiService.manageJar(request)
    }

    suspend fun deleteJar(id: Int): Response<ResponseBody> {
        val request = mapOf(
            "operation" to "delete",
            "id" to id
        )
        return apiService.manageJar(request)
    }

    suspend fun getJarsByLocation(locationId: Int): Response<ResponseBody> {
        val request = mapOf(
            "operation" to "getByLocation",
            "locationId" to locationId
        )
        return apiService.manageJar(request)
    }
}
