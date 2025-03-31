package com.example.tbcacademy.domain.usecase

import javax.inject.Inject

class AccountValidationUseCase @Inject constructor() {

    enum class AccountType {
        ACCOUNT_NUMBER, PERSONAL_NUMBER, PHONE_NUMBER
    }

    operator fun invoke(type: AccountType, input: String): ValidationResult {
        return when(type) {
            AccountType.ACCOUNT_NUMBER -> {
                if(input.length == 22) ValidationResult.Valid
                else ValidationResult.Invalid("Account number must be 22 characters")
            }
            AccountType.PHONE_NUMBER -> {
                if(input.length == 9 && input.all { it.isDigit() })
                    ValidationResult.Valid
                else ValidationResult.Invalid("Phone number must be 9 digits")
            }
            AccountType.PERSONAL_NUMBER -> {
                if(input.length == 11 && input.all{it.isDigit()}) ValidationResult.Valid
                else ValidationResult.Invalid("Personal number must be 11 digits")
            }
        }
    }

    sealed class ValidationResult {
        data object Valid : ValidationResult()
        data class Invalid(val message: String) : ValidationResult()
    }
}