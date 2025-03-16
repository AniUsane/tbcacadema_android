package com.example.tbcacademy.presentation.effect

sealed class LoginEffect {
    data object NavigateToProfile : LoginEffect()
    data class ShowSnackBar(val message: String) : LoginEffect()
    data object NavigateToRegister : LoginEffect()
}