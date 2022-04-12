package com.eneskayiklik.eventverse.feature_settings.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.core.model.ErrorState
import com.eneskayiklik.eventverse.core.util.Resource
import com.eneskayiklik.eventverse.core.util.Settings
import com.eneskayiklik.eventverse.core.util.UiEvent
import com.eneskayiklik.eventverse.feature_settings.data.repository.SettingsRepositoryImpl
import com.eneskayiklik.eventverse.feature_settings.data.state.SettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepositoryImpl
): ViewModel() {
    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state

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

    fun deleteAccountPopup() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(
                isLoading = false,
                errorDialogState = ErrorState(
                    title = "Delete Account Permanently",
                    subTitle = "Do you really want to delete your KOU Social account permanently ?",
                    firstButtonText = "No",
                    secondButtonText = "Yes",
                    firstButtonClick = { _state.value = _state.value.copy(
                        errorDialogState = null
                    ) },
                    secondButtonClick = { deleteAccount() }
                )
            )
        }
    }

    private fun deleteAccount() {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.deleteAccount().collectLatest {
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
}