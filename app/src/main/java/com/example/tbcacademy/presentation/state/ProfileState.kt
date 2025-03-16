package com.example.tbcacademy.presentation.state

sealed class ProfileState {
    data object Loading: ProfileState()
    data class Success(val email: String): ProfileState()
    data class Error(val message: String): ProfileState()
}