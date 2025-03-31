package com.example.tbcacademy.data.repository

import com.example.tbcacademy.data.mapper.toDomain
import com.example.tbcacademy.data.service.TransferService
import com.example.tbcacademy.domain.model.Card
import com.example.tbcacademy.domain.repository.TransferRepository

class TransferRepositoryImpl(
    private val service: TransferService
): TransferRepository {

    override suspend fun getAccounts(): List<Card> {
        return service.getCards().map {it.toDomain()}
    }

}