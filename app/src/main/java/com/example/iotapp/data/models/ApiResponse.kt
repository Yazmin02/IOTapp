package com.example.iotapp.data.models

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean
)
