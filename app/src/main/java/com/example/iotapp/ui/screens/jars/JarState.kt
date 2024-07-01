package com.example.iotapp.ui.screens.jars

import com.example.iotapp.data.models.Jar

data class JarState(
    val jars: List<Jar>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
