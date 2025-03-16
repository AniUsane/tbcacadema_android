package com.example.tbcacademy.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.base.BaseViewModel
import com.example.tbcacademy.data.remote.Resource
import com.example.tbcacademy.data.repository.RegisterRepository
import com.example.tbcacademy.domain.usecase.ValidateRegistrationUseCase
import com.example.tbcacademy.presentation.effect.RegisterEffect
import com.example.tbcacademy.presentation.event.RegisterEvent
import com.example.tbcacademy.presentation.state.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: RegisterRepository,
    private val validateRegistration: ValidateRegistrationUseCase
) : BaseViewModel<RegisterState, RegisterEvent, RegisterEffect>(RegisterState.Idle) {

    private fun register(email: String, password: String, repeatedPassword: String) {
        val validationResult = validateRegistration(email, password, repeatedPassword)

        if(validationResult is Resource.Error) {
            updateState { RegisterState.Error(validationResult.errorMessage) }
            return
        }

        viewModelScope.launch {
            updateState { RegisterState.Loading }
            repository.register(email, password).collect{ result ->
                when(result) {
                    is Resource.Success -> {
                        updateState { RegisterState.Success }
                        emitEffect(RegisterEffect.NavigateToLogin)
                    }
                    is Resource.Error -> {
                        updateState { RegisterState.Error(result.errorMessage) }
                    }
                    else -> Unit
                }
            }

        }
    }


    override fun obtainEvent(event: RegisterEvent) {
        when(event) {
            is RegisterEvent.SubmitRegistration -> register(event.email, event.password, event.repeatedPassword)
        }
    }

    fun navigateToLoginPage(){
        viewModelScope.launch {
            emitEffect(RegisterEffect.NavigateToLoginPage)
        }
    }

}