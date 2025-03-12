package com.example.tbcacademy.presentation.viewmodel

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.data.remote.Resource
import com.example.tbcacademy.data.repository.RegisterRepository
import com.example.tbcacademy.domain.usecase.ValidateRegistrationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: RegisterRepository,
    private val validateRegistration: ValidateRegistrationUseCase
) : ViewModel() {

    private val _registerState = MutableStateFlow<Resource<Unit>>(Resource.Default("Idle"))
    val registerState: StateFlow<Resource<Unit>> = _registerState

    fun register(email: String, password: String, repeatPassword: String) {
        val validationResult = validateRegistration(email, password, repeatPassword)

        if (validationResult is Resource.Error) {
            _registerState.value = validationResult
            return
        }

        viewModelScope.launch {
            repository.register(email, password).collect { result ->
                d("RegistrationViewModel", "Register result: $result")
                _registerState.value = result
            }
        }
    }

}