package com.example.tbcacademy.presentation.screen.ui.transfer

import com.example.tbcacademy.presentation.model.CardUi

data class TransferState (
    val cards: List<CardUi> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedFromCard: CardUi ? = null,
    val selectedToCard: CardUi? = null,
    val exchangeRate: Float? = null
)