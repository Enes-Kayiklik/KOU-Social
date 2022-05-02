package com.eneskayiklik.eventverse.feature.profile.settings.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.core.data.model.ErrorState
import com.eneskayiklik.eventverse.util.Resource
import com.eneskayiklik.eventverse.util.Settings
import com.eneskayiklik.eventverse.util.UiEvent
import com.eneskayiklik.eventverse.util.data_store.AppDataStore
import com.eneskayiklik.eventverse.data.repository.profile.SettingsRepositoryImpl
import com.eneskayiklik.eventverse.data.state.profile.SettingsState
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepositoryImpl,
    private val dataStore: AppDataStore
) : ViewModel() {
    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state

    val activeMode = dataStore.activeMode
    val activeLanguage = dataStore.activeLanguage

    private val _event = MutableSharedFlow<UiEvent>()
    val event: SharedFlow<UiEvent> = _event

    init {
        setState()
    }

    private fun setState() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                user = Settings.currentUser.toUser()
            )
        }
    }

    fun logOut() {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.logOut().collectLatest {
                when (it) {
                    is Resource.Error -> Unit
                    is Resource.Loading -> _state.value = _state.value.copy(
                        isLoading = true
                    )
                    is Resource.Success -> _event.emit(UiEvent.RestartApp)
                }
            }
        }
    }

    fun deleteAccountPopup(account: GoogleSignInAccount) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(
                isLoading = false,
                errorDialogState = ErrorState(
                    title = "Delete Account Permanently",
                    subTitle = "Do you really want to delete your KOU Social account permanently ?",
                    firstButtonText = "No",
                    secondButtonText = "Yes",
                    firstButtonClick = {
                        _state.value = _state.value.copy(
                            errorDialogState = null
                        )
                    },
                    secondButtonClick = { deleteAccount(account) }
                )
            )
        }
    }

    private fun deleteAccount(account: GoogleSignInAccount) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.deleteAccount(account.idToken ?: return@launch).collectLatest {
                when (it) {
                    is Resource.Error -> Unit
                    is Resource.Loading -> _state.value = _state.value.copy(
                        isLoading = true,
                        errorDialogState = null
                    )
                    is Resource.Success -> _event.emit(UiEvent.RestartApp)
                }
            }
        }
    }

    fun toggleTheme(value: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.setDarkMode(value)
        }
    }

    fun updateLanguage(value: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.setLanguage(value)
        }
    }

    fun updateCurrentUser() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                user = Settings.currentUser.toUser()
            )
        }
    }
}