package com.example.tbcacademy.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.tbcacademy.data.mapper.toDomain
import com.example.tbcacademy.data.remote.ProfileService
import com.example.tbcacademy.data.model.UserDto
import com.example.tbcacademy.domain.model.User
import com.example.tbcacademy.domain.repository.UserRepository
import com.example.tbcacademy.presentation.ui.UserPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val service: ProfileService
): UserRepository {

    override fun getUserPaging(): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                prefetchDistance = 2
            ),
            pagingSourceFactory = { UserPagingSource(service) }
        ).flow.map { pagingData ->
            pagingData.map { userDto ->
                userDto.toDomain()
            }
        }
    }

}