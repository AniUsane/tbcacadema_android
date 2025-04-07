package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.data.remote.common.Resource
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ValidateLoginUseCaseTest {
    private lateinit var validateLoginUseCase: ValidateLoginUseCase

    @Before
    fun setUp(){
        validateLoginUseCase = ValidateLoginUseCase()
    }

    @Test
    fun `Empty email and password returns error` () {
        val result = validateLoginUseCase("", "")
        assertTrue(result is Resource.Error)
        assertEquals("All fields must be filled.", (result as Resource.Error).errorMessage)
    }

    @Test
    fun `Empty email returns error` (){
        val result = validateLoginUseCase("", "password")
        assertTrue(result is Resource.Error)
        assertEquals("All fields must be filled.", (result as Resource.Error).errorMessage)
    }

    @Test
    fun `Empty password returns error` (){
        val result = validateLoginUseCase("test@gmail.com", "")
        assertTrue(result is Resource.Error)
        assertEquals("All fields must be filled.", (result as Resource.Error).errorMessage)
    }

    @Test
    fun `Email does not match returns error`(){
        val result = validateLoginUseCase("invalidEmail", "password")
        assertTrue(result is Resource.Error)
        assertEquals("Invalid email format.", (result as Resource.Error).errorMessage)
    }

    @Test
    fun `Valid email and password returns success`(){
        val result = validateLoginUseCase("test@reqres.in", "password")
        assertTrue(result is Resource.Success)
    }
}