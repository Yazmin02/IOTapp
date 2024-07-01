package com.example.iotapp.data.models

import com.google.gson.annotations.SerializedName

data class Jar(
    @SerializedName("Id") val id: Int? = null,
    @SerializedName("LocationId") val locationId: Int,
    @SerializedName("Description") val description: String
)
