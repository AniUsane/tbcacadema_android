package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.domain.model.Card
import com.example.tbcacademy.domain.repository.AccountRepository
import javax.inject.Inject

class GetToAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(accountNumber: String): Card {
        return repository.getToAccountInfo(accountNumber)
    }
}