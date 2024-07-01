package com.example.iotapp.data.models

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("Id") val id: Int? = null,
    @SerializedName("Name") val name: String,
    @SerializedName("Details") val details: String
)
