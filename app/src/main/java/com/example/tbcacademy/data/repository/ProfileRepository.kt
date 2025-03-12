package com.example.tbcacademy.data.repository

import com.example.tbcacademy.data.local.datastore.DataStoreManager
import com.example.tbcacademy.data.remote.ProfileService
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val profileService: ProfileService,
    private val dataStore: DataStoreManager
) {

}