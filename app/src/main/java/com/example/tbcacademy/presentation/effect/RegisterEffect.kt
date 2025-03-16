package com.example.tbcacademy.presentation.effect

sealed class RegisterEffect {
    data object NavigateToLogin: RegisterEffect()
    data class ShowSnackBar(val message: String): RegisterEffect()
    data object NavigateToLoginPage: RegisterEffect()
}