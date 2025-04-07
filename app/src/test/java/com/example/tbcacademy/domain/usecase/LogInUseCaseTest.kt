package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.data.local.datastore.DataStoreManager
import com.example.tbcacademy.data.remote.common.Resource
import com.example.tbcacademy.domain.repository.LoginRepository
import com.example.tbcacademy.presentation.ui.navigation.Screen
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class LogInUseCaseTest{
    private lateinit var logInUseCase: LogInUseCase
    private lateinit var repository: LoginRepository
    private lateinit var validateLogin: ValidateLoginUseCase
    private lateinit var dataStoreManager: DataStoreManager

    @Before
    fun setUp(){
        repository = mockk()
        validateLogin = mockk()
        dataStoreManager = mockk()

        logInUseCase = LogInUseCase(repository, validateLogin, dataStoreManager)
    }

    @Test
    fun `Invalid email or password returns error`() = runTest{
        every{validateLogin(any(), any())} returns Resource.Error("Invalid email")
        val result = logInUseCase("invalid", "123", false).first()
        assertTrue(result is Resource.Error)
        assertEquals("Invalid email", (result as Resource.Error).errorMessage)
    }

    @Test
    fun `Not stored credentials returns error`() = runTest {
        every { validateLogin(any(), any()) } returns Resource.Success(Unit)
        every {dataStoreManager.getUserCredentials()} returns flowOf(null to null)
        val result = logInUseCase("test@gmail.com", "password", false).first()
        assertTrue(result is Resource.Error)
        assertEquals("No registered user found. Please register first.", (result as Resource.Error).errorMessage)
    }

    @Test
    fun `Wrong email or password returns error`() = runTest {
        every { validateLogin(any(), any()) } returns Resource.Success(Unit)
        every { dataStoreManager.getUserCredentials() } returns flowOf("test@gmail.com" to "password12")
        val result = logInUseCase("test@mail.com", "password", false).first()
        assertTrue(result is Resource.Error)
        assertEquals("Incorrect email or password.", (result as Resource.Error).errorMessage)

    }

    @Test
    fun `Valid login returns success`() = runTest {
        every { validateLogin(any(), any()) } returns Resource.Success(Unit)
        every { dataStoreManager.getUserCredentials() } returns flowOf("test@gmail.com" to "123456")
        coEvery { repository.login(any(), any(), any()) } returns flowOf(Resource.Success("token"))
        val result = logInUseCase("test@gmail.com", "123456", false).first()
        assertTrue(result is Resource.Success)
        assertEquals("token", (result as Resource.Success).data)
    }

}