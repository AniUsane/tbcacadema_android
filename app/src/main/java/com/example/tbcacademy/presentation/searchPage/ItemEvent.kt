package com.example.tbcacademy.presentation.searchPage

sealed class ItemEvent {
    data object LoadItems: ItemEvent()
}