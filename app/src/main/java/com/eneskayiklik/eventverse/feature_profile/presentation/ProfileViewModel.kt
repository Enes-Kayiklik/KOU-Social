package com.eneskayiklik.eventverse.feature_profile.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.core.util.Resource
import com.eneskayiklik.eventverse.core.util.Settings
import com.eneskayiklik.eventverse.core.util.UiEvent
import com.eneskayiklik.eventverse.feature_profile.data.repository.ProfileRepositoryImpl
import com.eneskayiklik.eventverse.feature_profile.data.state.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    _stateHandle: SavedStateHandle,
    private val profileRepository: ProfileRepositoryImpl
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state

    private val _event = MutableSharedFlow<UiEvent>()
    val event: SharedFlow<UiEvent> = _event

    init {
        val userId = _stateHandle.get<String>("userId") ?: ""
        _state.value = _state.value.copy(
            userId = userId
        )
        getUserData(userId)
    }

    private fun getUserData(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (_state.value.isSelf) _state.value = _state.value.copy(
                user = Settings.currentUser.toUser(),
                isLoading = false
            )
            profileRepository.getUser(userId).collectLatest {
                when (it) {
                    is Resource.Error -> Unit
                    is Resource.Loading -> _state.value = _state.value.copy(
                        isLoading = true
                    )
                    is Resource.Success -> _state.value = _state.value.copy(
                        user = it.data,
                        isLoading = false
                    )
                }
            }
        }
    }
}