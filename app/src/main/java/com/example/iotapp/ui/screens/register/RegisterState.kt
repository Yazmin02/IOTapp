package com.example.iotapp.ui.screens.register

// Data class que representa el estado de la UI en la pantalla de registro
data class RegisterState(
    val name: String = "", // Nombre del usuario
    val email: String = "", // Email del usuario
    val password: String = "", // Contraseña del usuario
    val isRegistered: Boolean = false, // Indicador de si el usuario está registrado
    val isLoading: Boolean = false, // Indicador de si el registro está en proceso
    val error: String = "" // Mensaje de error en caso de fallo en el registro
)
