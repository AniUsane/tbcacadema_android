package com.example.tbcacademy.presentation.screen.ui.transfer

import com.example.tbcacademy.presentation.model.CardUi

sealed class TransferEvent {
    data object LoadCards: TransferEvent()
    data object FromCardClicked: TransferEvent()
    data object ToCardClicked: TransferEvent()
    data class OnCardSelected(val card: CardUi) : TransferEvent()
    data class OnToCardSelected(val card: CardUi) : TransferEvent()
    data class LoadExchangeRate(val from: String, val to: String) : TransferEvent()
}