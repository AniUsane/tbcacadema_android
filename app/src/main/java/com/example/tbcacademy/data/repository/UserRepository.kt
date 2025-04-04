package com.example.tbcacademy.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tbcacademy.data.remote.ProfileService
import com.example.tbcacademy.data.remote.User
import com.example.tbcacademy.presentation.ui.UserPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val service: ProfileService
) {

    fun getUserPaging(): Flow<PagingData<User>>{
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                prefetchDistance = 2
            ),
            pagingSourceFactory = { UserPagingSource(service) }
        ).flow
    }

}