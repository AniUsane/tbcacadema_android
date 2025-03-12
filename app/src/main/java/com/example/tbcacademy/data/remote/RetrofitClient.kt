package com.example.tbcacademy.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object RetrofitClient {

    private const val BASE_URL = "https://reqres.in/api/"

    val retrofit: ProfileService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(Json{ignoreUnknownKeys = true}
            .asConverterFactory("application/json".toMediaType()))
        .build()
        .create(ProfileService::class.java)
}