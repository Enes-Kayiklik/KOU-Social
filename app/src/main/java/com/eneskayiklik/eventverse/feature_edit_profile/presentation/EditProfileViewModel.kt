package com.eneskayiklik.eventverse.feature_edit_profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.core.util.UiEvent
import com.eneskayiklik.eventverse.feature_edit_profile.data.event.EditProfileEvent
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

) : ViewModel() {
    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state

    private val _event = MutableSharedFlow<UiEvent>()
    val event: SharedFlow<UiEvent> = _event

    fun onEvent(event: EditProfileEvent) = viewModelScope.launch {
        when (event) {
            is EditProfileEvent.OnDepartment -> _state.value = _state.value.copy(
                user = _state.value.user.copy(department = event.department),
                isDepartmentPopupVisible = false
            )
            is EditProfileEvent.OnFullName -> _state.value = _state.value.copy(
                user = _state.value.user.copy(fullName = event.text)
            )
            is EditProfileEvent.OnGitHub -> _state.value = _state.value.copy(
                user = _state.value.user.copy(socialAccounts = _state.value.user.socialAccounts.copy(
                    github = event.text
                ))
            )
            is EditProfileEvent.OnInstagram -> _state.value = _state.value.copy(
                user = _state.value.user.copy(socialAccounts = _state.value.user.socialAccounts.copy(
                    instagram = event.text
                ))
            )
            is EditProfileEvent.OnLinkedIn -> _state.value = _state.value.copy(
                user = _state.value.user.copy(socialAccounts = _state.value.user.socialAccounts.copy(
                    linkedIn = event.text
                ))
            )
            is EditProfileEvent.OnTwitter -> _state.value = _state.value.copy(
                user = _state.value.user.copy(socialAccounts = _state.value.user.socialAccounts.copy(
                    twitter = event.text
                ))
            )
            is EditProfileEvent.OnPhoto -> _state.value = _state.value.copy(
                user = _state.value.user.copy(profilePic = event.uri.toString())
            )
            is EditProfileEvent.OnBirthdate -> _state.value = _state.value.copy(
                user = _state.value.user.copy(birtDate = event.date)
            )
            is EditProfileEvent.ShowDepartmentPopup -> _state.value = _state.value.copy(
                isDepartmentPopupVisible = event.isVisible
            )
        }
    }
}