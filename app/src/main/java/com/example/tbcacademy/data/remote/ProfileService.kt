package com.example.tbcacademy.data.remote

import com.example.tbcacademy.data.model.Item
import retrofit2.http.GET

interface ProfileService {
    @GET("v3/499e0ffd-db69-4955-8d86-86ee60755b9c")
    suspend fun getItems(): List<Item>
}