package com.example.iotapp.data.network

import com.example.iotapp.data.network.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://backendiot.azurewebsites.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
