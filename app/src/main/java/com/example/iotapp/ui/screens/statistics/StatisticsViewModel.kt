package com.example.iotapp.ui.screens.statistics

import androidx.lifecycle.*
import com.example.iotapp.data.models.FillLevelTrend
import com.example.iotapp.data.repository.StatisticsRepository
import kotlinx.coroutines.launch

class StatisticsViewModel(private val repository: StatisticsRepository) : ViewModel() {
    private val _state = MutableLiveData(StatisticState())
    val state: LiveData<StatisticState> = _state

    fun loadFillLevelTrend(binId: Int, timeRange: String) {
        _state.value = StatisticState(isLoading = true)
        viewModelScope.launch {
            try {
                val trends = repository.getFillLevelTrend(binId, timeRange)
                _state.value = StatisticState(fillLevelTrend = trends, isLoading = false)
            } catch (e: Exception) {
                _state.value = StatisticState(error = e.message, isLoading = false)
            }
        }
    }
}
