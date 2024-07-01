package com.example.iotapp.ui.screens.jars

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iotapp.data.models.Jar
import com.example.iotapp.data.network.ApiClient
import com.example.iotapp.data.repository.JarRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class JarViewModel : ViewModel() {
    private val jarRepository = JarRepository(ApiClient.apiService)

    private val _uiState = MutableStateFlow(JarState())
    val uiState: StateFlow<JarState> = _uiState

    fun createJar(locationId: Int, description: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val response = jarRepository.createJar(Jar(locationId = locationId, description = description))
            if (response.isSuccessful) {
                readJarsByLocation(locationId)
            } else {
                _uiState.value = _uiState.value.copy(isLoading = false, error = "Error creating jar")
            }
        }
    }

    fun readJars() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val response = jarRepository.readJars()
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    val jarType = object : TypeToken<List<Jar>>() {}.type
                    val jars: List<Jar> = Gson().fromJson(body.string(), jarType)
                    _uiState.value = _uiState.value.copy(jars = jars, isLoading = false)
                }
            } else {
                _uiState.value = _uiState.value.copy(isLoading = false, error = "Error reading jars")
            }
        }
    }

    fun updateJar(id: Int, locationId: Int, description: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val response = jarRepository.updateJar(Jar(id = id, locationId = locationId, description = description))
            if (response.isSuccessful) {
                readJarsByLocation(locationId)
            } else {
                _uiState.value = _uiState.value.copy(isLoading = false, error = "Error updating jar")
            }
        }
    }

    fun deleteJar(id: Int, locationId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val response = jarRepository.deleteJar(id)
            if (response.isSuccessful) {
                readJarsByLocation(locationId)
            } else {
                _uiState.value = _uiState.value.copy(isLoading = false, error = "Error deleting jar")
            }
        }
    }

    fun readJarsByLocation(locationId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val response = jarRepository.getJarsByLocation(locationId)
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    val jarType = object : TypeToken<List<Jar>>() {}.type
                    val jars: List<Jar> = Gson().fromJson(body.string(), jarType)
                    _uiState.value = _uiState.value.copy(jars = jars, isLoading = false)
                }
            } else {
                _uiState.value = _uiState.value.copy(isLoading = false, error = "Error fetching jars by location")
            }
        }
    }
}
