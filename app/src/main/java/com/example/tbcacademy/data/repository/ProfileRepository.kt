package com.example.tbcacademy.data.repository

import com.example.tbcacademy.data.local.datastore.DataStoreManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val dataStore: DataStoreManager
) {
    fun getUserEmail(): Flow<String?> = dataStore.getUserEmail()

    suspend fun clearSession(){
        dataStore.clearAuthData()
    }
}