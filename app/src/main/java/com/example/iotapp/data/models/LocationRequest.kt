package com.example.iotapp.data.models

data class LocationRequest(
    val operation: String,
    val id: Int? = null,
    val name: String? = null,
    val details: String? = null
)
