package com.eneskayiklik.eventverse.feature_edit_profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.core.util.Resource
import com.eneskayiklik.eventverse.core.util.UiEvent
import com.eneskayiklik.eventverse.core.util.extension.isValidFullName
import com.eneskayiklik.eventverse.core.util.extension.isValidGitHubUsername
import com.eneskayiklik.eventverse.core.util.extension.isValidInstagramUsername
import com.eneskayiklik.eventverse.core.util.extension.isValidTwitterUsername
import com.eneskayiklik.eventverse.feature_edit_profile.data.event.EditProfileEvent
import com.eneskayiklik.eventverse.feature_edit_profile.data.repository.EditProfileRepositoryImpl
import com.eneskayiklik.eventverse.feature_settings.data.state.SettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val editProfileRepository: EditProfileRepositoryImpl
) : ViewModel() {
    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state

    private val _event = MutableSharedFlow<UiEvent>()
    val event: SharedFlow<UiEvent> = _event

    fun onEvent(event: EditProfileEvent) = viewModelScope.launch {
        when (event) {
            is EditProfileEvent.OnDepartment -> _state.value = _state.value.copy(
                department = event.department,
                isDepartmentPopupVisible = false
            )
            is EditProfileEvent.OnFullName -> _state.value = _state.value.copy(
                fullName = _state.value.fullName.copy(text = event.text, error = "")
            )
            is EditProfileEvent.OnGitHub -> _state.value = _state.value.copy(
                github = _state.value.github.copy(text = event.text, error = "")
            )
            is EditProfileEvent.OnInstagram -> _state.value = _state.value.copy(
                instagram = _state.value.instagram.copy(text = event.text, error = "")
            )
            is EditProfileEvent.OnLinkedIn -> _state.value = _state.value.copy(
                linkedIn = _state.value.linkedIn.copy(text = event.text, error = "")
            )
            is EditProfileEvent.OnTwitter -> _state.value = _state.value.copy(
                twitter = _state.value.twitter.copy(text = event.text, error = "")
            )
            is EditProfileEvent.OnPhoto -> _state.value = _state.value.copy(
                profilePic = event.uri.toString(),
                isProfilePicUpdated = true
            )
            is EditProfileEvent.OnBirthdate -> _state.value = _state.value.copy(
                birthDate = event.date
            )
            is EditProfileEvent.ShowDepartmentPopup -> _state.value = _state.value.copy(
                isDepartmentPopupVisible = event.isVisible
            )
        }
    }

    fun updateProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = _state.value
            val validFullName = data.fullName.text.isValidFullName().not()
            val validInsta = data.instagram.text.isValidInstagramUsername().not()
            val validTwitter = data.twitter.text.isValidTwitterUsername().not()
            val validGithub = data.github.text.isValidGitHubUsername().not()
            val validLinkedin = data.linkedIn.text.isValidTwitterUsername().not()
            if (validFullName || validInsta || validTwitter || validGithub || validLinkedin) {
                _state.value = _state.value.copy(
                    fullName = _state.value.fullName.copy(error = if (validFullName) "Full name must be valid" else ""),
                    instagram = _state.value.instagram.copy(error = if (validInsta) "Instagram username must be valid" else ""),
                    twitter = _state.value.twitter.copy(error = if (validTwitter) "Twitter username must be valid" else ""),
                    github = _state.value.github.copy(error = if (validGithub) "GitHub username must be valid" else ""),
                    linkedIn = _state.value.linkedIn.copy(error = if (validLinkedin) "LinkedIn username must be valid" else ""),
                )
                return@launch
            }

            editProfileRepository.updateUser(data.getFieldsAsUser(), data.isProfilePicUpdated).collectLatest {
                when (it) {
                    is Resource.Error -> _state.value = _state.value.copy(
                        isLoading = false
                    )
                    is Resource.Loading -> _state.value = _state.value.copy(
                        isLoading = true
                    )
                    is Resource.Success -> _event.emit(UiEvent.ClearBackStack)
                }
            }
        }
    }
}