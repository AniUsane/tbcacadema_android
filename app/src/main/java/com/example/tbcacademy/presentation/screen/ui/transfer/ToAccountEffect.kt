package com.example.tbcacademy.presentation.screen.ui.transfer

import com.example.tbcacademy.presentation.model.CardUi

sealed class ToAccountEffect {
    data class ShowError(val message: String) : ToAccountEffect()
    data class SubmitSuccess(val account: CardUi) : ToAccountEffect()
    data class SubmitToAccount(val card: CardUi) : ToAccountEffect()

}