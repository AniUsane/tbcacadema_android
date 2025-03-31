package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.domain.model.Card
import com.example.tbcacademy.domain.repository.TransferRepository

class GetCardsUseCase(
    private val repository: TransferRepository
) {
    suspend operator fun invoke(): List<Card> {
        return repository.getAccounts()
    }
}