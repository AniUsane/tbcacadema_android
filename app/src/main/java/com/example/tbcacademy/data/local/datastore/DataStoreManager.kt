package com.example.tbcacademy.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    companion object{
        private val AUTH_TOKEN = stringPreferencesKey("auth_token")
        private val USER_EMAIL = stringPreferencesKey("user_email")
        private val USER_PASSWORD = stringPreferencesKey("user_password")
    }

    suspend fun saveAuthToken(token: String){
        dataStore.edit { preferences ->
            preferences[AUTH_TOKEN] = token
        }
    }

    fun getAuthToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[AUTH_TOKEN]
        }
    }

    suspend fun saveUserCredentials(email: String, password: String) {
        dataStore.edit { preferences ->
            preferences[USER_EMAIL] = email
            preferences[USER_PASSWORD] = password
        }
    }

    fun getUserCredentials(): Flow<Pair<String?, String?>> {
        return dataStore.data.map { preferences ->
            Pair(preferences[USER_EMAIL], preferences[USER_PASSWORD])
        }
    }

    fun getUserEmail(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[USER_EMAIL]
        }
    }

    suspend fun clearAuthData() {
        dataStore.edit { preferences ->
            preferences.remove(AUTH_TOKEN)
        }
    }
}