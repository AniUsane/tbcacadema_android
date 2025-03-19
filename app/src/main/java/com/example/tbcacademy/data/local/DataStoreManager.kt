package com.example.tbcacademy.data.local

import androidx.datastore.core.DataStore
import java.util.prefs.Preferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreManager @Inject constructor(dataStore: DataStore<Preferences>) {
}