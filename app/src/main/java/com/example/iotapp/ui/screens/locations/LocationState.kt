package com.example.iotapp.ui.screens.locations

import com.example.iotapp.data.models.Location

// Data class que representa el estado de la UI en la pantalla de locaciones
data class LocationState(
    val locations: List<Location>? = null, // Lista de ubicaciones
    val isLoading: Boolean = false, // Indicador de si la carga está en proceso
    val error: String? = null // Mensaje de error en caso de fallo en la carga de datos
)
