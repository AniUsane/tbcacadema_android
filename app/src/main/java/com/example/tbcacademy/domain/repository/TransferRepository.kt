package com.example.tbcacademy.domain.repository

import com.example.tbcacademy.domain.model.Card

interface TransferRepository {
    suspend fun getAccounts(): List<Card>
}