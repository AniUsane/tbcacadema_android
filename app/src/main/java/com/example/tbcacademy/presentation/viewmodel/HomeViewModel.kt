package com.example.tbcacademy.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.tbcacademy.data.remote.User
import com.example.tbcacademy.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: UserRepository
): ViewModel() {
    val userList: Flow<PagingData<User>> = repository.getUserPaging().cachedIn(viewModelScope)
}