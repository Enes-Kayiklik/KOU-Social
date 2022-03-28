package com.eneskayiklik.eventverse.feature_message.presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.eneskayiklik.eventverse.feature_message.data.state.MessagesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel @Inject constructor(
    _savedState: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(MessagesState())
    val state: StateFlow<MessagesState> = _state

    init {
        _state.value = _state.value.copy(
            channelId = _savedState.get<String>("channelId") ?: ""
        )
    }
}