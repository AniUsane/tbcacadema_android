package com.example.tbcacademy.di

import com.example.tbcacademy.data.repository.LoginRepositoryImpl
import com.example.tbcacademy.data.repository.ProfileRepositoryImpl
import com.example.tbcacademy.data.repository.RegisterRepositoryImpl
import com.example.tbcacademy.data.repository.UserRepositoryImpl
import com.example.tbcacademy.domain.repository.LoginRepository
import com.example.tbcacademy.domain.repository.ProfileRepository
import com.example.tbcacademy.domain.repository.RegisterRepository
import com.example.tbcacademy.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        implementation: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindProfileRepository(
        implementation: ProfileRepositoryImpl
    ): ProfileRepository

    @Binds
    @Singleton
    abstract fun bindLoginRepository(
        implementation: LoginRepositoryImpl
    ): LoginRepository

    @Binds
    @Singleton
    abstract fun bindRegisterRepository(
        implementation: RegisterRepositoryImpl
    ): RegisterRepository
}