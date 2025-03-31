package com.example.tbcacademy.data.mapper

import com.example.tbcacademy.data.model.CardDto
import com.example.tbcacademy.domain.model.Card

fun CardDto.toDomain() =
    Card(
        id = id,
        accountName = accountName,
        accountNumber = accountNumber,
        currencyType = currencyType,
        cardType = cardType,
        balance = balance,
        cardLogo = cardLogo ?: ""
    )