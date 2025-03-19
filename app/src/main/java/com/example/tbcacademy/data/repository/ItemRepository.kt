package com.example.tbcacademy.data.repository

import com.example.tbcacademy.data.model.Item
import com.example.tbcacademy.data.remote.ProfileService
import javax.inject.Inject

class ItemRepository @Inject constructor(
    private val api: ProfileService
) {
    suspend fun getItems(): List<Item>{
        return api.getItems()
    }
}