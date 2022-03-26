package com.eneskayiklik.eventverse.feature_share.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.core.util.UiEvent
import com.eneskayiklik.eventverse.feature_share.data.event.ShareEvent
import com.eneskayiklik.eventverse.feature_share.data.repository.ShareRepositoryImpl
import com.eneskayiklik.eventverse.feature_share.data.state.ShareState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShareViewModel @Inject constructor(
    private val shareRepository: ShareRepositoryImpl
): ViewModel() {

    private val _state = MutableStateFlow(ShareState())
    val state: StateFlow<ShareState> = _state

    private val _event = MutableSharedFlow<UiEvent>()
    val event: SharedFlow<UiEvent> = _event

    fun onEvent(event: ShareEvent) {
        viewModelScope.launch {
            when (event) {
                is ShareEvent.OnTitle -> _state.value = _state.value.copy(
                    titleState = _state.value.titleState.copy(
                        text = event.text
                    )
                )
                is ShareEvent.OnBody -> _state.value = _state.value.copy(
                    bodyState = _state.value.bodyState.copy(
                        text = event.text
                    )
                )
            }
        }
    }

    fun sharePost() {
        viewModelScope.launch(Dispatchers.IO) {
            val body = _state.value.bodyState.text
            val title = _state.value.titleState.text
            if (body.isNotEmpty() && _state.value.isLoading.not()) {
                _state.value = _state.value.copy(
                    isLoading = true
                )
                val result = shareRepository.sharePost(title, body)
                _state.value = _state.value.copy(
                    isLoading = true
                )
                if (result.isSuccess) _event.emit(UiEvent.ClearBackStack)
            }
        }
    }
}