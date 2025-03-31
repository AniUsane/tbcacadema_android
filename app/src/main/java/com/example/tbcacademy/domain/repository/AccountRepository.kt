package com.example.tbcacademy.domain.repository

import com.example.tbcacademy.domain.model.Card

interface AccountRepository {
    suspend fun getToAccountInfo(accountNumber: String): Card
}