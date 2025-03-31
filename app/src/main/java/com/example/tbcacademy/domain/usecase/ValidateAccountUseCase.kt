package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.domain.repository.AccountRepository

class ValidateAccountUseCase(
    private val repository: AccountRepository
) {
//    suspend operator fun invoke(accountNumber: String): Boolean {
//        return repository.verifyAccount(accountNumber)
//    }
}