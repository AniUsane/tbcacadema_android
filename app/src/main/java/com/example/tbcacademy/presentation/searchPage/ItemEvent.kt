package com.example.tbcacademy.presentation.searchPage

sealed class ItemEvent {
    data class LoadItems(val query: String): ItemEvent()
}