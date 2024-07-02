package com.example.iotapp.data.models

import com.google.gson.annotations.SerializedName

data class JarRequest(
    @SerializedName("operation") val operation: String,
    @SerializedName("id") val id: Int? = null,
    @SerializedName("locationId") val locationId: Int? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("status") val status: Int? = null
)
