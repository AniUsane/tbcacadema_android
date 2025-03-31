package com.example.tbcacademy.data.service

import com.example.tbcacademy.data.model.AccountVerificationDto
import com.example.tbcacademy.data.model.CardDto
import retrofit2.http.GET
import retrofit2.http.Query

interface TransferService {

    @GET("d689fe3e-6faf-446a-9896-c538de3449fa")
    suspend fun getCards(): List<CardDto>

//    @GET("29d002d4-3ccd-4eaa-95eb-a9d1601ce123")
//    suspend fun getVerificationStatus(@Query("account_number") accountNumber: String): AccountVerificationDto

}