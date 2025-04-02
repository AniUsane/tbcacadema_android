package com.example.tbcacademy.presentation.state

sealed class LoginState {
    data object Loading: LoginState()
    data class Success(val token: String): LoginState()
    data class Error(val message: String): LoginState()
    data class Input(
        val email: String = "",
        val password: String = "",
        val isRememberMeChecked: Boolean = false
    ): LoginState()
}