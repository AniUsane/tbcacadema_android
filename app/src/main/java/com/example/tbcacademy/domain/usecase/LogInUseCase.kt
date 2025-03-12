package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.data.remote.Resource
import com.example.tbcacademy.data.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LogInUseCase @Inject constructor(
    private val repository: LoginRepository,
    private val validateLogin: ValidateLoginUseCase
) {
    operator fun invoke(email: String, password: String, rememberMe: Boolean): Flow<Resource<String>> = flow {
        val validationResult = validateLogin(email, password)

        if (validationResult is Resource.Error) {
            emit(validationResult)
            return@flow
        }

        repository.login(email, password, rememberMe).collect { result ->
            emit(result)
        }
    }
}