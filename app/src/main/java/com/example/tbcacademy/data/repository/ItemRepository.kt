package com.example.tbcacademy.data.repository

import com.example.tbcacademy.data.model.Item
import com.example.tbcacademy.data.model.Resource
import com.example.tbcacademy.data.remote.HandleHttpRequests
import com.example.tbcacademy.data.remote.ProfileService
import javax.inject.Inject

class ItemRepository @Inject constructor(
    private val api: ProfileService
) {
    suspend fun getItems(): Resource<List<Item>> {
        return HandleHttpRequests.handleHttpRequest { api.getItems() }
    }
}