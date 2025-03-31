package com.example.tbcacademy.presentation.mapper

import com.example.tbcacademy.domain.model.Card
import com.example.tbcacademy.presentation.model.CardUi

fun Card.toPresentation(): CardUi {
    return CardUi(
        id = id,
        title = accountName,
        cardType = cardType,
        maskedAccountNumber = "*** ${accountNumber.takeLast(4)}",
        balanceFormatted = "$balance $currencyType",
        cardLogo = cardLogo ?: ""
    )
}