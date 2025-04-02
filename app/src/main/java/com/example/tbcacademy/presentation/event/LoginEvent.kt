package com.example.tbcacademy.presentation.event

sealed class LoginEvent {
    data class SubmitLogin(val email: String, val password: String, val rememberMe: Boolean): LoginEvent()
    data object CheckRememberMe: LoginEvent()

    data class EmailChanged(val email: String): LoginEvent()
    data class PasswordChanged(val password: String): LoginEvent()
    data class RememberMeChanged(val isChecked: Boolean): LoginEvent()
}