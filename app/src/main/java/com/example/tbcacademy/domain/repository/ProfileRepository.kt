package com.example.tbcacademy.domain.repository

import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getUserEmail(): Flow<String?>
    suspend fun clearSession()
}