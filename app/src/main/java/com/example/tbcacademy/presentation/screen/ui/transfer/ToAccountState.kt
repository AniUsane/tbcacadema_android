package com.example.tbcacademy.presentation.screen.ui.transfer

import com.example.tbcacademy.domain.usecase.AccountValidationUseCase

data class ToAccountState(
    val selectedType: AccountValidationUseCase.AccountType = AccountValidationUseCase.AccountType.ACCOUNT_NUMBER,
    val input: String = "",
    val isValid: Boolean = false,
    val error: String? = null
)
