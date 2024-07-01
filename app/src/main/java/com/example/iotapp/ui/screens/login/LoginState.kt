package com.example.iotapp.ui.screens.login

// Data class que representa el estado de la UI en la pantalla de login
data class LoginState(
    val email: String = "", // Email del usuario
    val password: String = "", // Contraseña del usuario
    val isAuthenticated: Boolean = false, // Indicador de si el usuario está autenticado
    val isLoading: Boolean = false, // Indicador de si la autenticación está en proceso
    val error: String = "" // Mensaje de error en caso de fallo en la autenticación
)
