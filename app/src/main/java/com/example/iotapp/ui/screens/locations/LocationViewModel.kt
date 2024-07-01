package com.example.iotapp.ui.screens.locations

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iotapp.data.models.Location
import com.example.iotapp.data.network.ApiClient
import com.example.iotapp.data.repository.LocationRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocationViewModel : ViewModel() {
    private val locationRepository = LocationRepository(ApiClient.apiService)
    private val _uiState = MutableStateFlow(LocationState())
    val uiState: StateFlow<LocationState> = _uiState

    init {
        readLocations()
    }

    fun createLocation(name: String, details: String) {
        viewModelScope.launch {
            val response = locationRepository.createLocation(Location(name = name, details = details))
            if (response.isSuccessful) {
                readLocations()
            }
        }
    }

    fun readLocations() {
        viewModelScope.launch {
            val response = locationRepository.readLocations()
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    val json = body.string()
                    Log.d("LocationViewModel", "Response JSON: $json")
                    val locationType = object : TypeToken<List<Location>>() {}.type
                    val locations: List<Location> = Gson().fromJson(json, locationType)
                    Log.d("LocationViewModel", "Parsed locations: $locations")
                    _uiState.value = _uiState.value.copy(locations = locations)
                }
            } else {
                _uiState.value = _uiState.value.copy(error = "Error fetching locations")
            }
        }
    }

    fun updateLocation(id: Int, name: String, details: String) {
        viewModelScope.launch {
            val response = locationRepository.updateLocation(Location(id = id, name = name, details = details))
            if (response.isSuccessful) {
                readLocations()
            }
        }
    }

    fun deleteLocation(id: Int) {
        viewModelScope.launch {
            val response = locationRepository.deleteLocation(id)
            if (response.isSuccessful) {
                readLocations()
            }
        }
    }
}
