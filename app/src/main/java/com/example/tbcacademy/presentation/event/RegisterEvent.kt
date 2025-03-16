package com.example.tbcacademy.presentation.event

sealed class RegisterEvent {
    data class SubmitRegistration(val email: String, val password: String, val repeatedPassword: String): RegisterEvent()
}