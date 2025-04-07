package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.data.remote.common.Resource
import javax.inject.Inject

class ValidateLoginUseCase @Inject constructor() {

    private val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"

    operator fun invoke(email: String, password: String): Resource<Unit> {
        if(email.isBlank() || password.isBlank()) {
            return Resource.Error("All fields must be filled.")
        }

        if(!Regex(emailRegex).matches(email)){
            return Resource.Error("Invalid email format.")
        }

        return Resource.Success(Unit)
    }
}