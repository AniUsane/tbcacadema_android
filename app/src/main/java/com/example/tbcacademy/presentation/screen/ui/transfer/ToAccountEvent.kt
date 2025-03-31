package com.example.tbcacademy.presentation.screen.ui.transfer
import com.example.tbcacademy.domain.usecase.AccountValidationUseCase.AccountType

sealed class ToAccountEvent {
    data class TypeSelected(val type: AccountType) : ToAccountEvent()
    data class InputChanged(val input: String) : ToAccountEvent()
    data object ContinueClicked : ToAccountEvent()

}