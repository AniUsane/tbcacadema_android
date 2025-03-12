package com.example.tbcacademy.data.remote

sealed class Resource<out T> {
    data class Success<out T>(val data: T): Resource<T>()
    data class Error(val errorMessage: String): Resource<Nothing>()
    data class Default(val default: String): Resource<Nothing>()
    object Loading: Resource<Nothing>()
}