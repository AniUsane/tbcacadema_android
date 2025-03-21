package com.example.tbcacademy.data.remote

import com.example.tbcacademy.data.model.Resource
import retrofit2.HttpException
import java.io.IOException

object HandleHttpRequests {
    suspend fun <T> handleHttpRequest(apiCall: suspend () -> T): Resource<T> {
        return try {
            val response = apiCall.invoke()
            Resource.Success(data = response)
        } catch (throwable: Throwable) {
            when (throwable) {
                is HttpException -> {
                    Resource.Error(errorMessage = throwable.message ?: "Registration failed.")
                }

                is IOException -> {
                    Resource.Error(errorMessage = throwable.message ?: "No internet connection.")
                }

                else -> {
                    Resource.Error(errorMessage = throwable.message ?: "Unexpected error occurred.")
                }
            }
        }
    }
}