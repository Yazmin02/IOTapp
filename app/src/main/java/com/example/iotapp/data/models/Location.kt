package com.example.iotapp.data.models

// Data class que representa una ubicación
data class Location(
    val id: Int? = null, // Identificador de la ubicación (puede ser nulo para nuevas ubicaciones)
    val name: String, // Nombre de la ubicación
    val details: String // Detalles adicionales sobre la ubicación
)
