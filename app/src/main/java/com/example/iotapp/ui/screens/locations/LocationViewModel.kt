package com.example.iotapp.ui.screens.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iotapp.data.repository.LocationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocationViewModel : ViewModel() {
    private val locationRepository = LocationRepository()

    private val _state = MutableStateFlow(LocationState())
    val state: StateFlow<LocationState> = _state

    init {
        fetchLocations()
    }

    private fun fetchLocations() {
        _state.value = _state.value.copy(isLoading = true)

        viewModelScope.launch {
            try {
                val response = locationRepository.getLocations()
                if (response.isSuccessful) {
                    _state.value = _state.value.copy(locations = response.body() ?: emptyList(), isLoading = false)
                } else {
                    _state.value = _state.value.copy(error = "Error fetching locations", isLoading = false)
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = "An error occurred", isLoading = false)
            }
        }
    }

    fun createLocation(name: String, details: String) {
        viewModelScope.launch {
            try {
                val response = locationRepository.createLocation(name, details)
                if (response.isSuccessful) {
                    fetchLocations()
                } else {
                    _state.value = _state.value.copy(error = "Error creating location")
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = "An error occurred")
            }
        }
    }

    fun updateLocation(id: Int, name: String, details: String) {
        viewModelScope.launch {
            try {
                val response = locationRepository.updateLocation(id, name, details)
                if (response.isSuccessful) {
                    fetchLocations()
                } else {
                    _state.value = _state.value.copy(error = "Error updating location")
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = "An error occurred")
            }
        }
    }

    fun deleteLocation(id: Int) {
        viewModelScope.launch {
            try {
                val response = locationRepository.deleteLocation(id)
                if (response.isSuccessful) {
                    fetchLocations()
                } else {
                    _state.value = _state.value.copy(error = "Error deleting location")
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = "An error occurred")
            }
        }
    }
}
