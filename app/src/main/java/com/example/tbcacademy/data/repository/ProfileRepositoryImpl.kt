package com.example.tbcacademy.data.repository

import com.example.tbcacademy.data.local.datastore.DataStoreManager
import com.example.tbcacademy.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val dataStore: DataStoreManager
): ProfileRepository {
    override fun getUserEmail(): Flow<String?> = dataStore.getUserEmail()

    override suspend fun clearSession(){
        dataStore.clearAuthData()
    }
}