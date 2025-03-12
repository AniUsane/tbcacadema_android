package com.example.tbcacademy.data.remote

import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

object HandleHttpRequests {
    suspend fun <T> handleHttpRequest(apiCall: suspend () -> Response<T>): Resource<T> {
        return try {
            val response = apiCall.invoke()
            if (response.isSuccessful) {
                response.body()?.let {
                    Resource.Success(data = it)
                } ?: Resource.Error(errorMessage = "Error")
            } else {
                Resource.Error(errorMessage = response.message())
            }
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