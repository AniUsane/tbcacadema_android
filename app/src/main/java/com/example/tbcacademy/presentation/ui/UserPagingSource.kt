package com.example.tbcacademy.presentation.ui

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tbcacademy.data.remote.ProfileService
import com.example.tbcacademy.data.model.UserDto

class UserPagingSource(
    private val service: ProfileService
): PagingSource<Int, UserDto>() {
    override fun getRefreshKey(state: PagingState<Int, UserDto>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserDto> {
        return try{
            val position = params.key ?: 1
            val response = service.getUsers(position)

            if (!response.isSuccessful || response.body() == null) {
                return LoadResult.Error(Exception("API response error: ${response.message()}"))
            }

            val userList = response.body()?.data ?: emptyList()

            LoadResult.Page(
                data = userList,
                prevKey = if(position == 1) null else (position - 1),
                nextKey = if(userList.isEmpty()) null else (position + 1)
            )
        }catch(e: Exception){
            LoadResult.Error(e)
        }
    }
}