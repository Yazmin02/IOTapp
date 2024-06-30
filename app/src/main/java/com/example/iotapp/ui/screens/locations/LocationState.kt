package com.example.iotapp.ui.screens.locations

import com.example.iotapp.data.models.Location

data class LocationState(
    val locations: List<Location> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
