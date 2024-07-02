package com.example.iotapp.data.models

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("FillLevelTrend")
    val fillLevelTrend: List<FillLevelTrend>,
    @SerializedName("FillFrequency")
    val fillFrequency: List<FillFrequency>
)

data class FillLevelTrend(
    @SerializedName("Timestamp")
    val timestamp: String,
    @SerializedName("FillLevel")
    val fillLevel: Float
)

data class FillFrequency(
    @SerializedName("FillLevel")
    val fillLevel: Float,
    @SerializedName("Count")
    val count: Int
)
