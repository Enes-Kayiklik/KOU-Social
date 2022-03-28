package com.eneskayiklik.eventverse.feature_profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.eneskayiklik.eventverse.feature_profile.data.state.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    _stateHandle: SavedStateHandle
): ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state

    init {
        val userId = _stateHandle.get<String>("userId") ?: ""
        _state.value = _state.value.copy(
            userId = userId
        )
    }
}