package com.example.iotapp.data.repository

import com.example.iotapp.data.network.ApiService
import okhttp3.ResponseBody
import retrofit2.Response

class StatisticsRepository(private val apiService: ApiService) {
    suspend fun getEventHistory(binId: Int, timeRange: String): Response<ResponseBody> {
        return apiService.getEventHistory(binId, timeRange)
    }
}
