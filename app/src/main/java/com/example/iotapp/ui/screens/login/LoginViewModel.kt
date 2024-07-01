package com.example.iotapp.ui.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iotapp.data.models.User
import com.example.iotapp.data.network.ApiClient
import com.example.iotapp.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException

// ViewModel para la pantalla de login
class LoginViewModel : ViewModel() {
    // Repositorio de usuario para manejar la lógica de autenticación
    private val userRepository = UserRepository(ApiClient.apiService)

    // Estado mutable que mantiene el estado de la UI
    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState

    // Función para manejar cambios en el campo de email
    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    // Función para manejar cambios en el campo de contraseña
    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    // Función para autenticar al usuario
    fun authenticateUser() {
        val email = _uiState.value.email
        val password = _uiState.value.password

        // Lanzar una coroutine para manejar la autenticación en segundo plano
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                // Llamar al repositorio para autenticar al usuario
                val response = userRepository.authenticateUser(User(name = "", email = email, password = password))
                Log.d("LoginViewModel", "Request: email=$email, password=$password")
                Log.d("LoginViewModel", "Response Code: ${response.code()}")
                Log.d("LoginViewModel", "Response Body: ${response.body()?.string()}")

                if (response.isSuccessful) {
                    // Actualizar el estado de la UI si la autenticación es exitosa
                    _uiState.value = _uiState.value.copy(isAuthenticated = true, isLoading = false)
                } else {
                    // Manejar el error si la autenticación falla
                    val errorBody = response.errorBody()?.string()
                    Log.d("LoginViewModel", "Error Body: $errorBody")
                    _uiState.value = _uiState.value.copy(error = "Autenticación fallida: $errorBody", isLoading = false)
                }
            } catch (e: IOException) {
                // Manejar errores de red
                Log.e("LoginViewModel", "Error de red", e)
                _uiState.value = _uiState.value.copy(error = "Error de red: ${e.message}", isLoading = false)
            } catch (e: HttpException) {
                // Manejar errores HTTP
                Log.e("LoginViewModel", "Error HTTP", e)
                _uiState.value = _uiState.value.copy(error = "Error HTTP: ${e.message}", isLoading = false)
            } catch (e: Exception) {
                // Manejar otros errores desconocidos
                Log.e("LoginViewModel", "Error desconocido", e)
                _uiState.value = _uiState.value.copy(error = "Error desconocido: ${e.message}", isLoading = false)
            }
        }
    }
}
