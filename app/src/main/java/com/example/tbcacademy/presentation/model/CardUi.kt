package com.example.tbcacademy.presentation.model

import java.io.Serializable

data class CardUi(
    val id: Int,
    val title: String,
    val cardType: String,
    val maskedAccountNumber: String,
    val balanceFormatted: String,
    val cardLogo: String
): Serializable
