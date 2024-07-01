package com.example.iotapp.data.repository

import com.example.iotapp.data.models.Location
import com.example.iotapp.data.network.ApiService
import okhttp3.ResponseBody
import retrofit2.Response

// Repositorio para manejar las operaciones relacionadas con las ubicaciones
class LocationRepository(private val apiService: ApiService) {

    // Función para crear una nueva ubicación
    suspend fun createLocation(location: Location): Response<ResponseBody> {
        // Se crea un mapa con la operación "create" y los datos de la ubicación
        val request = mapOf(
            "operation" to "create",
            "name" to location.name,
            "details" to location.details
        )
        // Se llama al endpoint de manageLocation con el mapa de datos
        return apiService.manageLocation(request)
    }

    // Función para leer todas las ubicaciones
    suspend fun readLocations(): Response<ResponseBody> {
        // Se crea un mapa con la operación "read"
        val request = mapOf("operation" to "read")
        // Se llama al endpoint de manageLocation con el mapa de datos
        return apiService.manageLocation(request)
    }

    // Función para actualizar una ubicación existente
    suspend fun updateLocation(location: Location): Response<ResponseBody> {
        // Se crea un mapa con la operación "update" y los datos actualizados de la ubicación
        val request = mapOf(
            "operation" to "update",
            "id" to location.id!!,
            "name" to location.name,
            "details" to location.details
        )
        // Se llama al endpoint de manageLocation con el mapa de datos
        return apiService.manageLocation(request)
    }

    // Función para eliminar una ubicación
    suspend fun deleteLocation(id: Int): Response<ResponseBody> {
        // Se crea un mapa con la operación "delete" y el ID de la ubicación
        val request = mapOf(
            "operation" to "delete",
            "id" to id
        )
        // Se llama al endpoint de manageLocation con el mapa de datos
        return apiService.manageLocation(request)
    }
}
