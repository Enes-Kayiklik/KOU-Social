package com.eneskayiklik.eventverse.util.data_store

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.eneskayiklik.eventverse.data.model.profile.DarkMode
import com.eneskayiklik.eventverse.data.model.profile.Language
import com.eneskayiklik.eventverse.data.model.profile.toLanguage
import com.eneskayiklik.eventverse.data.model.profile.toModeEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    private val darkModeKey = intPreferencesKey("dark_mode_key")
    private val languageKey = intPreferencesKey("language_key")

    val activeMode: Flow<DarkMode> = dataStore.data
        .map { preferences ->
            (preferences[darkModeKey] ?: 2).toModeEnum()
        }

    suspend fun setDarkMode(modeIndex: Int) {
        dataStore.edit { preferences ->
            preferences[darkModeKey] = modeIndex
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