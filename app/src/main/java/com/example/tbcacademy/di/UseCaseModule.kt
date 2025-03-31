package com.example.tbcacademy.di

import com.example.tbcacademy.domain.repository.AccountRepository
import com.example.tbcacademy.domain.repository.TransferRepository
import com.example.tbcacademy.domain.usecase.GetCardsUseCase
import com.example.tbcacademy.domain.usecase.GetToAccountUseCase
import com.example.tbcacademy.domain.usecase.ValidateAccountUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun provideGetCardsUseCase(repository: TransferRepository): GetCardsUseCase {
        return GetCardsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideValidateUseCase(repository: AccountRepository): ValidateAccountUseCase {
        return ValidateAccountUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetToAccountUseCase(
        repository: AccountRepository
    ): GetToAccountUseCase = GetToAccountUseCase(repository)
}