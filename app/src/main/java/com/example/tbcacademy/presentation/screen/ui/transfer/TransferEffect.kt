package com.example.tbcacademy.presentation.screen.ui.transfer

import com.example.tbcacademy.presentation.model.CardUi

sealed class TransferEffect {
    data class ShowError(val message: String): TransferEffect()
    data object ClickFromBottomSheet: TransferEffect()
    data object ClickToBottomSheet: TransferEffect()
    data class SubmitToCard(val account: CardUi) : TransferEffect()
}