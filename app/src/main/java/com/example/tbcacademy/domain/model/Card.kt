package com.example.tbcacademy.domain.model

data class Card(
    val id: Int,
    val accountName: String,
    val accountNumber: String,
    val currencyType: String,
    val cardType: String,
    val balance: Int,
    val cardLogo: String? = null
)
