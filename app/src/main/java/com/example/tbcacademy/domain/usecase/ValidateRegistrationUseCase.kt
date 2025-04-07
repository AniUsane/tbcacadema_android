package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.data.remote.common.Resource
import javax.inject.Inject

class ValidateRegistrationUseCase @Inject constructor() {

    private val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"

    operator fun invoke(email: String, password: String, repeatedPassword: String): Resource<Unit> {
        if(email.isBlank() || password.isBlank() || repeatedPassword.isBlank()){
            return Resource.Error("All fields must be filled.")
        }

        if(!Regex(emailRegex).matches(email)) {
            return Resource.Error("Invalid email format.")
        }

        if(password.length < 6) {
            return Resource.Error("Password must be at least 6 characters.")
        }

        if(password != repeatedPassword) {
            return Resource.Error("Passwords do not match.")
        }

        if(email != "eve.holt@reqres.in"){
            return Resource.Error("Wrong email address.")
        }

        return Resource.Success(Unit)

    }
}