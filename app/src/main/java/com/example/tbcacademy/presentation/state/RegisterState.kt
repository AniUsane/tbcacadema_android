package com.example.tbcacademy.presentation.state

sealed class RegisterState {
    data object Loading: RegisterState()
    data object Success: RegisterState()
    data class Error(val message: String): RegisterState()
    data class Input(
        val email: String = "",
        val password: String = "",
        val repeatedPassword: String = "",
        val username: String = ""
    ): RegisterState()
}