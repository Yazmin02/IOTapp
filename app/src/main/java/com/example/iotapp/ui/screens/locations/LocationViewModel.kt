package com.example.iotapp.ui.screens.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iotapp.data.models.Location
import com.example.iotapp.data.network.ApiClient
import com.example.iotapp.data.repository.LocationRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// ViewModel para la pantalla de locaciones
class LocationViewModel : ViewModel() {
    // Repositorio de ubicaciones para manejar la lógica de las locaciones
    private val locationRepository = LocationRepository(ApiClient.apiService)

    // Estado mutable que mantiene el estado de la UI
    private val _uiState = MutableStateFlow(LocationState())
    val uiState: StateFlow<LocationState> = _uiState

    // Inicializar el ViewModel leyendo las ubicaciones
    init {
        readLocations()
    }

    // Función para crear una nueva ubicación
    fun createLocation(name: String, details: String) {
        viewModelScope.launch {
            // Llamar al repositorio para crear la ubicación
            val response = locationRepository.createLocation(Location(name = name, details = details))
            if (response.isSuccessful) {
                // Leer las ubicaciones nuevamente después de crear una nueva
                readLocations()
            }
        }
    }

    // Función para leer las ubicaciones
    fun readLocations() {
        viewModelScope.launch {
            // Llamar al repositorio para leer las ubicaciones
            val response = locationRepository.readLocations()
            if (response.isSuccessful) {
                // Si la respuesta es exitosa, convertir el cuerpo de la respuesta a una lista de ubicaciones
                response.body()?.let { body ->
                    val locationType = object : TypeToken<List<Location>>() {}.type
                    val locations: List<Location> = Gson().fromJson(body.string(), locationType)
                    // Actualizar el estado de la UI con la lista de ubicaciones
                    _uiState.value = _uiState.value.copy(locations = locations)
                }
            }
        }
    }

    // Función para actualizar una ubicación existente
    fun updateLocation(id: Int, name: String, details: String) {
        viewModelScope.launch {
            // Llamar al repositorio para actualizar la ubicación
            val response = locationRepository.updateLocation(Location(id = id, name = name, details = details))
            if (response.isSuccessful) {
                // Leer las ubicaciones nuevamente después de actualizar una existente
                readLocations()
            }
        }
    }

    // Función para eliminar una ubicación
    fun deleteLocation(id: Int) {
        viewModelScope.launch {
            // Llamar al repositorio para eliminar la ubicación
            val response = locationRepository.deleteLocation(id)
            if (response.isSuccessful) {
                // Leer las ubicaciones nuevamente después de eliminar una
                readLocations()
            }
        }
    }
}
