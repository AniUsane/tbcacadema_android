package com.example.tbcacademy.data.repository

import com.example.tbcacademy.data.local.datastore.DataStoreManager
import com.example.tbcacademy.data.remote.common.HandleHttpRequests
import com.example.tbcacademy.data.remote.ProfileService
import com.example.tbcacademy.data.model.Request
import com.example.tbcacademy.data.remote.common.Resource
import com.example.tbcacademy.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val profileService: ProfileService,
    private val dataStoreManager: DataStoreManager
): LoginRepository {
    override suspend fun login(email: String, password: String, rememberMe: Boolean): Flow<Resource<String>> = flow {
        emit(Resource.Loading)

        val result = HandleHttpRequests.handleHttpRequest {
            profileService.login(Request(email, password))
        }

        when(result) {
            is Resource.Success -> {
                if(rememberMe) {
                    dataStoreManager.saveAuthToken(result.data.token)
                }
                emit(Resource.Success(result.data.token))
            }
            is Resource.Error -> {
                emit(Resource.Error(result.errorMessage))
            }

            else -> Unit
        }
    }

    override fun getAuthToken(): Flow<String?> = dataStoreManager.getAuthToken()

    override suspend fun saveAuthToken(token: String) {
        dataStoreManager.saveAuthToken(token)
    }
}