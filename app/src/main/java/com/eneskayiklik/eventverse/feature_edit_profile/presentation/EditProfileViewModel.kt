package com.eneskayiklik.eventverse.feature_edit_profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.core.util.Settings
import com.eneskayiklik.eventverse.core.util.UiEvent
import com.eneskayiklik.eventverse.feature_settings.data.state.SettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(

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
}