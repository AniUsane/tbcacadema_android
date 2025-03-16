package com.example.tbcacademy.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.base.BaseViewModel
import com.example.tbcacademy.data.repository.ProfileRepository
import com.example.tbcacademy.presentation.effect.ProfileEffect
import com.example.tbcacademy.presentation.event.ProfileEvent
import com.example.tbcacademy.presentation.state.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
): BaseViewModel<ProfileState, ProfileEvent, ProfileEffect>(ProfileState.Loading) {
    override fun obtainEvent(event: ProfileEvent) {
        when(event){
            is ProfileEvent.FetchUserEmail -> fetchUserEmail()
            is ProfileEvent.Logout -> logout()
        }
    }

    private fun fetchUserEmail(){
        viewModelScope.launch {
            repository.getUserEmail().collect{ email ->
                if(email.isNullOrEmpty()){
                    updateState { ProfileState.Error("No email found") }
                }else{
                    updateState { ProfileState.Success(email) }
                }
            }
        }
    }

    private fun logout(){
        viewModelScope.launch {
            repository.clearSession()
            emitEffect(ProfileEffect.NavigateToLogin)
        }
    }

}