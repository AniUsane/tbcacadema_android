package com.example.tbcacademy.presentation.event

sealed class RegisterEvent {
    data class SubmitRegistration(val email: String, val password: String, val repeatedPassword: String): RegisterEvent()
    data class EmailChanged(val email: String): RegisterEvent()
    data class UsernameChanged(val username: String): RegisterEvent()
    data class PasswordChanged(val password: String): RegisterEvent()
    data class RepeatedPasswordChanged(val repeatedPassword: String): RegisterEvent()
}