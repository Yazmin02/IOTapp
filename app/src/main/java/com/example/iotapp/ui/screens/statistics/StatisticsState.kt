package com.example.iotapp.ui.screens.statistics

import com.example.iotapp.data.models.FillLevelTrend

data class StatisticState(
    val isLoading: Boolean = true,
    val fillLevelTrend: List<FillLevelTrend> = emptyList(),
    val error: String? = null
)
