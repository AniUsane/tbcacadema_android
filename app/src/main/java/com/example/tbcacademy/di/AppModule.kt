package com.example.tbcacademy.di

import android.content.Context
import com.example.tbcacademy.data.repository.UploadImageRepository
import com.example.tbcacademy.domain.usecase.UploadImageToFirebaseUseCase
import com.example.tbcacademy.domain.usecase.UploadImageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideUploadImageUseCase(): UploadImageUseCase {
        return UploadImageUseCase()
    }

    @Provides
    @Singleton
    fun provideUploadImageToFirebaseUseCase(): UploadImageToFirebaseUseCase {
        return UploadImageToFirebaseUseCase()
    }

    @Provides
    @Singleton
    fun provideUploadImageRepository(
        uploadImageUseCase: UploadImageUseCase,
        uploadImageToFirebaseUseCase: UploadImageToFirebaseUseCase,
        @ApplicationContext context: Context
    ): UploadImageRepository {
        return UploadImageRepository(
            uploadImageUseCase,
            uploadImageToFirebaseUseCase,
            context
        )
    }
}