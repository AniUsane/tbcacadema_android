package com.example.tbcacademy.presentation.screen

sealed class UploadImageEffect {
    data class ShowError(val message: String): UploadImageEffect()
}