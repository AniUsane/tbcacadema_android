package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.data.local.datastore.DataStoreManager
import com.example.tbcacademy.data.remote.Resource
import com.example.tbcacademy.data.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LogInUseCase @Inject constructor(
    private val repository: LoginRepository,
    private val validateLogin: ValidateLoginUseCase,
    private val dataStoreManager: DataStoreManager
) {
    operator fun invoke(email: String, password: String, rememberMe: Boolean): Flow<Resource<String>> = flow {
        val validationResult = validateLogin(email, password)

        if (validationResult is Resource.Error) {
            emit(validationResult)
            return@flow
        }

        dataStoreManager.getUserCredentials().collect { storedCredentials ->
            val (storedEmail, storedPassword) = storedCredentials

            if (storedEmail == null || storedPassword == null) {
                emit(Resource.Error("No registered user found. Please register first."))
                return@collect
            }

            if (email != storedEmail || password != storedPassword) {
                emit(Resource.Error("Incorrect email or password."))
                return@collect
            }

            repository.login(email, password, rememberMe).collect { result ->
                emit(result)
            }
        }
    }
}