package com.example.tbcacademy.presentation.searchPage

sealed class ItemEffect {
    data class ShowError(val message: String): ItemEffect()
}