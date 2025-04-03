package com.example.tbcacademy.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val data: List<User>
)