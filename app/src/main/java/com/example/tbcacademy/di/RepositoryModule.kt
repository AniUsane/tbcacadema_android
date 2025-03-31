package com.example.tbcacademy.di

import com.example.tbcacademy.data.repository.AccountRepositoryImpl
import com.example.tbcacademy.data.repository.TransferRepositoryImpl
import com.example.tbcacademy.data.service.TransferService
import com.example.tbcacademy.domain.repository.AccountRepository
import com.example.tbcacademy.domain.repository.TransferRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTransferRepository(service: TransferService): TransferRepository {
        return TransferRepositoryImpl(service)
    }

    @Provides
    @Singleton
    fun provideAccountRepository(service: TransferService): AccountRepository {
        return AccountRepositoryImpl(service)
    }
}