package com.example.iotapp.ui.screens.statistics

data class StatisticsState(
    val fillLevelTrend: List<FillLevelTrend>? = null,
    val fillFrequency: List<FillFrequency>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

data class FillLevelTrend(val Timestamp: String, val FillLevel: Float)
data class FillFrequency(val FillLevel: Float, val Count: Int)
