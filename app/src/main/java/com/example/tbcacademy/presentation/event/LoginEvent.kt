package com.example.tbcacademy.presentation.event

sealed class LoginEvent {
    data class SubmitLogin(val email: String, val password: String, val rememberMe: Boolean): LoginEvent()
    data object CheckRememberMe: LoginEvent()
}