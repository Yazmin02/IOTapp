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

class LoginViewModel : ViewModel() {
    private val userRepository = UserRepository(ApiClient.apiService)

    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun authenticateUser() {
        val email = _uiState.value.email
        val password = _uiState.value.password

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val response = userRepository.authenticateUser(User(email, password))
                Log.d("LoginViewModel", "Request: email=$email, password=$password")
                Log.d("LoginViewModel", "Response Code: ${response.code()}")
                Log.d("LoginViewModel", "Response Body: ${response.body()?.string()}")

                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(isAuthenticated = true, isLoading = false)
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.d("LoginViewModel", "Error Body: $errorBody")
                    _uiState.value = _uiState.value.copy(error = "Autenticación fallida: $errorBody", isLoading = false)
                }
            } catch (e: IOException) {
                Log.e("LoginViewModel", "Error de red", e)
                _uiState.value = _uiState.value.copy(error = "Error de red: ${e.message}", isLoading = false)
            } catch (e: HttpException) {
                Log.e("LoginViewModel", "Error HTTP", e)
                _uiState.value = _uiState.value.copy(error = "Error HTTP: ${e.message}", isLoading = false)
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Error desconocido", e)
                _uiState.value = _uiState.value.copy(error = "Error desconocido: ${e.message}", isLoading = false)
            }
        }
    }
}
