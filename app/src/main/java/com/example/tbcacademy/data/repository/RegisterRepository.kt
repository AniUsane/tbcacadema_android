package com.example.tbcacademy.data.repository

import com.example.tbcacademy.data.remote.HandleHttpRequests
import com.example.tbcacademy.data.remote.ProfileService
import com.example.tbcacademy.data.remote.Request
import com.example.tbcacademy.data.remote.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterRepository @Inject constructor(
    private val profileService: ProfileService
) {

    suspend fun register(email: String, password: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)
        val result = HandleHttpRequests.handleHttpRequest {
            profileService.register(Request(email, password))
        }
        when (result) {
            is Resource.Success -> emit(Resource.Success(Unit))
            is Resource.Error -> emit(Resource.Error(result.errorMessage))
            else -> emit(Resource.Error("Unexpected error"))
        }
    }
}