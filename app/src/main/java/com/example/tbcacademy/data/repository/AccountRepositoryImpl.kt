package com.example.tbcacademy.data.repository

import com.example.tbcacademy.data.mapper.toDomain
import com.example.tbcacademy.data.service.TransferService
import com.example.tbcacademy.domain.model.Card
import com.example.tbcacademy.domain.repository.AccountRepository

class AccountRepositoryImpl(
    private val service: TransferService
): AccountRepository {
    override suspend fun getToAccountInfo(accountNumber: String): Card {
        val allCards = service.getCards()
        val matchedCard = allCards.find { it.accountNumber == accountNumber }
            ?: throw Exception("Could not find the account")

        return matchedCard.toDomain()
    }


}