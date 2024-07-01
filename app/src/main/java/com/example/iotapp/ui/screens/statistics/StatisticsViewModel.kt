package com.example.iotapp.ui.screens.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iotapp.data.repository.StatisticsRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StatisticsViewModel(private val statisticsRepository: StatisticsRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(StatisticsState())
    val uiState: StateFlow<StatisticsState> = _uiState

    fun fetchStatistics(binId: Int, timeRange: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val response = statisticsRepository.getEventHistory(binId, timeRange)
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    val json = body.string()
                    val responseMap = Gson().fromJson<Map<String, Any>>(json, object : TypeToken<Map<String, Any>>() {}.type)
                    val fillLevelTrendJson = Gson().toJson(responseMap["FillLevelTrend"])
                    val fillFrequencyJson = Gson().toJson(responseMap["FillFrequency"])
                    val fillLevelTrend = Gson().fromJson<List<FillLevelTrend>>(fillLevelTrendJson, object : TypeToken<List<FillLevelTrend>>() {}.type)
                    val fillFrequency = Gson().fromJson<List<FillFrequency>>(fillFrequencyJson, object : TypeToken<List<FillFrequency>>() {}.type)
                    _uiState.value = _uiState.value.copy(fillLevelTrend = fillLevelTrend, fillFrequency = fillFrequency, isLoading = false)
                }
            } else {
                _uiState.value = _uiState.value.copy(isLoading = false, error = "Error fetching statistics")
            }
        }
    }
}
