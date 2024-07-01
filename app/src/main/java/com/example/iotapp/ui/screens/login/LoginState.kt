package com.example.iotapp.ui.screens.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isAuthenticated: Boolean = false,
    val isLoading: Boolean = false,
    val error: String = ""
)
