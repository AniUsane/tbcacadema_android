package com.example.tbcacademy.domain.repository

import com.example.tbcacademy.data.remote.common.Resource
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    suspend fun register(email: String, password: String): Flow<Resource<Unit>>
}