package com.example.tbcacademy.domain.repository

import com.example.tbcacademy.data.remote.common.Resource
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun login(email: String, password: String, rememberMe: Boolean): Flow<Resource<String>>
    fun getAuthToken(): Flow<String?>
    suspend fun saveAuthToken(token: String)
}