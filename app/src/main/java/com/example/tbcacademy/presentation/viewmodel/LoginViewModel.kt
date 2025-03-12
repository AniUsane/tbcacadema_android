package com.example.tbcacademy.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.data.remote.Resource
import com.example.tbcacademy.data.repository.LoginRepository
import com.example.tbcacademy.domain.usecase.LogInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LogInUseCase,
    private val repository: LoginRepository
): ViewModel() {

    private val _loginState = MutableStateFlow<Resource<String>>(Resource.Default("Idle"))
    val loginState: StateFlow<Resource<String>> = _loginState

    init {
        checkRememberMe()
    }

    private fun checkRememberMe(){
        viewModelScope.launch {
            repository.getAuthToken().collect { token ->
                if(!token.isNullOrEmpty()){
                    _loginState.value = Resource.Success(token)
                }
            }
        }
    }

    fun login(email: String, password: String, rememberMe: Boolean){
        viewModelScope.launch {
            loginUseCase(email, password, rememberMe).collect{result ->
                _loginState.value = result
            }
        }
    }

}