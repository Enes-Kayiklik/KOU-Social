package com.eneskayiklik.eventverse.util.data_store

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.eneskayiklik.eventverse.data.model.profile.Language
import com.eneskayiklik.eventverse.data.model.profile.toLanguage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    private val darkModeKey = booleanPreferencesKey("dark_mode")
    private val languageKey = intPreferencesKey("language_key")

    val isDarkModeEnabled: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[darkModeKey] ?: false
        }

    suspend fun setDarkMode(isEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[darkModeKey] = isEnabled
        }
    }

    val activeLanguage: Flow<Language> = dataStore.data
        .map { preferences ->
            (preferences[languageKey] ?: -1).toLanguage()
        }

    suspend fun setLanguage(languageIndex: Int) {
        dataStore.edit { preferences ->
            preferences[languageKey] = languageIndex
        }
    }
}