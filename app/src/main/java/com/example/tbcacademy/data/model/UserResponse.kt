package com.example.tbcacademy.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val data: List<UserDto>
)