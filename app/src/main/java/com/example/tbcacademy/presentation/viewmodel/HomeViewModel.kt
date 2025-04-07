package com.example.tbcacademy.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.tbcacademy.data.model.UserDto
import com.example.tbcacademy.data.repository.UserRepositoryImpl
import com.example.tbcacademy.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: UserRepositoryImpl
): ViewModel() {
    val userList: Flow<PagingData<User>> = repository.getUserPaging().cachedIn(viewModelScope)
}