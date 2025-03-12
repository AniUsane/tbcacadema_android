package com.example.tbcacademy.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProfileService {
    @POST("login")
    suspend fun login(@Body loginRequest: Request): Response<LoginResponse>

    @POST("register")
    suspend fun register(@Body registerRequest: Request): Response<RegisterResponse>

    @GET("users")
    suspend fun getUsers(@Query("page") page: Int): Response<List<User>>
}