package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.data.remote.common.Resource
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ValidateRegistrationUseCaseTest{
    private lateinit var validateRegistrationUseCase: ValidateRegistrationUseCase

    @Before
    fun setUp(){
        validateRegistrationUseCase = ValidateRegistrationUseCase()
    }

    @Test
    fun `Empty email password and repeated password returns error` (){
        val result = validateRegistrationUseCase("", "", "")
        assertTrue(result is Resource.Error)
        assertEquals("All fields must be filled.", (result as Resource.Error).errorMessage)
    }

    @Test
    fun `Invalid email returns error` (){
        val result = validateRegistrationUseCase("InvalidEmail", "password", "password")
        assertTrue(result is Resource.Error)
        assertEquals("Invalid email format.", (result as Resource.Error).errorMessage)
    }

    @Test
    fun `Password has less than 6 chars returns error` (){
        val result = validateRegistrationUseCase("test@gmail.com", "123", "123")
        assertTrue(result is Resource.Error)
        assertEquals("Password must be at least 6 characters.", (result as Resource.Error).errorMessage)
    }

    @Test
    fun `Passwords do not match returns error` (){
        val result = validateRegistrationUseCase("test@gmail.com", "1234567", "123456")
        assertTrue(result is Resource.Error)
        assertEquals("Passwords do not match.", (result as Resource.Error).errorMessage)
    }

    @Test
    fun `Email is not for eve holt returns error`(){
        val result = validateRegistrationUseCase("test@gmail.com", "123456", "123456")
        assertTrue(result is Resource.Error)
        assertEquals("Wrong email address.", (result as Resource.Error).errorMessage)
    }

    @Test
    fun `Valid inputs return success`(){
        val result = validateRegistrationUseCase("eve.holt@reqres.in", "123456", "123456")
        assertTrue(result is Resource.Success)
    }

}