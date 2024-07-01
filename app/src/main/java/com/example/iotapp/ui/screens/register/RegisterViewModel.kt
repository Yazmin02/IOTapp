package com.example.iotapp.ui.screens.register

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

// ViewModel para la pantalla de registro
class RegisterViewModel : ViewModel() {
    // Repositorio de usuario para manejar la lógica de registro
    private val userRepository = UserRepository(ApiClient.apiService)

    // Estado mutable que mantiene el estado de la UI
    private val _uiState = MutableStateFlow(RegisterState())
    val uiState: StateFlow<RegisterState> = _uiState

    // Función para manejar cambios en el campo de nombre
    fun onNameChange(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    // Función para manejar cambios en el campo de email
    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    // Función para manejar cambios en el campo de contraseña
    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    // Función para registrar al usuario
    fun registerUser() {
        val name = _uiState.value.name
        val email = _uiState.value.email
        val password = _uiState.value.password

        // Lanzar una coroutine para manejar el registro en segundo plano
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                // Llamar al repositorio para registrar al usuario
                val response = userRepository.registerUser(User(name = name, email = email, password = password))
                Log.d("RegisterViewModel", "Request: name=$name, email=$email, password=$password")
                Log.d("RegisterViewModel", "Response Code: ${response.code()}")
                Log.d("RegisterViewModel", "Response Body: ${response.body()?.string()}")

                if (response.isSuccessful) {
                    // Actualizar el estado de la UI si el registro es exitoso
                    _uiState.value = _uiState.value.copy(isRegistered = true, isLoading = false)
                } else {
                    // Manejar el error si el registro falla
                    val errorBody = response.errorBody()?.string()
                    Log.d("RegisterViewModel", "Error Body: $errorBody")
                    _uiState.value = _uiState.value.copy(error = "Registro fallido: $errorBody", isLoading = false)
                }
            } catch (e: IOException) {
                // Manejar errores de red
                Log.e("RegisterViewModel", "Error de red", e)
                _uiState.value = _uiState.value.copy(error = "Error de red: ${e.message}", isLoading = false)
            } catch (e: HttpException) {
                // Manejar errores HTTP
                Log.e("RegisterViewModel", "Error HTTP", e)
                _uiState.value = _uiState.value.copy(error = "Error HTTP: ${e.message}", isLoading = false)
            } catch (e: Exception) {
                // Manejar otros errores desconocidos
                Log.e("RegisterViewModel", "Error desconocido", e)
                _uiState.value = _uiState.value.copy(error = "Error desconocido: ${e.message}", isLoading = false)
            }
        }
    }
}
