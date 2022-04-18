package com.eneskayiklik.eventverse.util.data_store

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    private val darkModeKey = booleanPreferencesKey("dark_mode")

    val isDarkModeEnabled: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[darkModeKey] ?: false
        }

    suspend fun setDarkMode(isEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[darkModeKey] = isEnabled
        }
    }
}