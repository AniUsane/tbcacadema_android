package com.example.tbcacademy.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Request(
    val email: String,
    val password: String
)
