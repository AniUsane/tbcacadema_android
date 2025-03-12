package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.data.remote.Resource
import javax.inject.Inject

class ValidateLoginUseCase @Inject constructor() {

    operator fun invoke(email: String, password: String): Resource<Unit> {
        if(email.isBlank() || password.isBlank()) {
            return Resource.Error("All fields must be filled.")
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return Resource.Error("Invalid email format.")
        }

        return Resource.Success(Unit)
    }
}