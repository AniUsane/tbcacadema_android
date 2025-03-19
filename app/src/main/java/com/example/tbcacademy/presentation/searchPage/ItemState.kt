package com.example.tbcacademy.presentation.searchPage

import com.example.tbcacademy.data.model.Item

sealed class ItemState {
    data object Loading: ItemState()
    data class Success(val items: List<Item>) : ItemState()
    data class Error(val message: String) : ItemState()
}