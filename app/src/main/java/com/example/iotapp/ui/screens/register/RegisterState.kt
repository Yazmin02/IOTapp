package com.example.iotapp.ui.screens.register

data class RegisterState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val isRegistered: Boolean = false,
    val isLoading: Boolean = false,
    val error: String = ""
)
