package com.example.iotapp.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iotapp.data.models.User
import com.example.iotapp.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val userRepository = UserRepository()

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    fun onEmailChange(email: String) {
        _state.value = _state.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _state.value = _state.value.copy(password = password)
    }

    fun authenticateUser() {
        val email = _state.value.email
        val password = _state.value.password

        if (email.isBlank() || password.isBlank()) {
            _state.value = _state.value.copy(error = "Email and Password cannot be empty")
            return
        }

        _state.value = _state.value.copy(isLoading = true, error = null)

        viewModelScope.launch {
            try {
                val response = userRepository.authenticateUser(User(email, password))
                if (response.isSuccessful && response.body()?.success == true) {
                    _state.value = _state.value.copy(isLoading = false)
                    // Handle successful login (e.g., navigate to another screen)
                } else {
                    _state.value = _state.value.copy(isLoading = false, error = "Authentication failed")
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = "An error occurred")
            }
        }
    }
}
