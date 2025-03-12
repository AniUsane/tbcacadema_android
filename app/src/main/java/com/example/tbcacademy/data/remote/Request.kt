package com.example.tbcacademy.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class Request(
    val email: String,
    val password: String
)
