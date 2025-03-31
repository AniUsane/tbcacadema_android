package com.example.tbcacademy.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CardDto(
    val id: Int,
    @SerialName("account_name")
    val accountName: String,
    @SerialName("account_number")
    val accountNumber: String,
    @SerialName("valute_type")
    val currencyType: String,
    @SerialName("card_type")
    val cardType: String,
    val balance: Int,
    @SerialName("card_logo")
    val cardLogo: String? = null
)
