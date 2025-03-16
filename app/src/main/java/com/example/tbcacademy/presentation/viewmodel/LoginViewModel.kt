package com.example.tbcacademy.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.base.BaseViewModel
import com.example.tbcacademy.data.remote.Resource
import com.example.tbcacademy.data.repository.LoginRepository
import com.example.tbcacademy.domain.usecase.LogInUseCase
import com.example.tbcacademy.presentation.effect.LoginEffect
import com.example.tbcacademy.presentation.event.LoginEvent
import com.example.tbcacademy.presentation.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LogInUseCase,
    private val repository: LoginRepository
): BaseViewModel<LoginState, LoginEvent, LoginEffect>(LoginState.Idle) {


    private fun checkRememberMe(){
        viewModelScope.launch {
            repository.getAuthToken().collect{ token ->
                if(!token.isNullOrEmpty()){
                    updateState { LoginState.Success(token) }
                    emitEffect(LoginEffect.NavigateToProfile)
                }
            }
        }
    }

    private fun login(email: String, password: String, rememberMe: Boolean){
        viewModelScope.launch {
            updateState { LoginState.Loading }
            loginUseCase(email, password, rememberMe).collect{ result ->
                when(result){
                    is Resource.Success -> {
                        if(rememberMe){
                            repository.saveAuthToken(result.data)
                        }
                        updateState { LoginState.Success(result.data) }
                        emitEffect(LoginEffect.NavigateToProfile)
                    }
                    is Resource.Error -> {
                        updateState { LoginState.Error(result.errorMessage) }
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun obtainEvent(event: LoginEvent) {
        when(event) {
            is LoginEvent.SubmitLogin -> login(event.email, event.password, event.rememberMe)
            is LoginEvent.CheckRememberMe -> checkRememberMe()
        }
    }

    fun navigateToRegister(){
        viewModelScope.launch {
            emitEffect(LoginEffect.NavigateToRegister)
        }
    }

    fun checkSession(){
        viewModelScope.launch {
            repository.getAuthToken().collect{ token ->
                if(!token.isNullOrEmpty()){
                    updateState { LoginState.Success(token) }
                    emitEffect(LoginEffect.NavigateToProfile)
                }
            }
        }
    }

}