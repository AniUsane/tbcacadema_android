package com.example.tbcacademy.presentation.searchPage

import com.example.tbcacademy.domain.model.DomainItem

sealed class ItemState {
    data object Loading: ItemState()
    data class Success(val items: List<DomainItem>) : ItemState()
    data class Error(val message: String) : ItemState()
}