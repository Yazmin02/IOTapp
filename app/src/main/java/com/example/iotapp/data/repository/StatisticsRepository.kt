package com.example.iotapp.data.repository

import com.example.iotapp.data.models.ApiResponse
import com.example.iotapp.data.models.FillLevelTrend
import com.example.iotapp.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StatisticsRepository(private val apiService: ApiService) {
    suspend fun getFillLevelTrend(binId: Int, timeRange: String): List<FillLevelTrend> {
        return withContext(Dispatchers.IO) {
            val response: ApiResponse = apiService.getEventHistory(binId, timeRange)
            response.fillLevelTrend
        }
    }
}
