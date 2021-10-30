package com.eneskayiklik.eventverse.feature_create.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.core.util.UiEvent
import com.eneskayiklik.eventverse.feature_create.domain.use_case.CreateEventUseCase
import com.eneskayiklik.eventverse.feature_create.presentation.util.CreateSectionState
import com.eneskayiklik.eventverse.feature_create.presentation.util.CreateState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val useCase: CreateEventUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(CreateSectionState())
    val state: StateFlow<CreateSectionState> = _state

    private val _createButtonState = MutableStateFlow(false)
    val createButtonState: StateFlow<Boolean> = _createButtonState

    private val _uiState = MutableSharedFlow<UiEvent>()
    val uiState: SharedFlow<UiEvent> = _uiState

    fun onCreateState(
        data: CreateState
    ) {
        when (data) {
            is CreateState.OnDescription -> {
                updateDescription(data.description)
            }
            is CreateState.OnTitle -> {
                updateTitle(data.title)
            }
            is CreateState.OnEndDate -> {
                _state.value = _state.value.copy(
                    endDate = data.date
                )
            }
            is CreateState.OnStartDate -> {
                _state.value = _state.value.copy(
                    startDate = data.date
                )
            }
            is CreateState.OnEndTime -> {
                _state.value = _state.value.copy(
                    endTime = data.time
                )
            }
            is CreateState.OnStartTime -> {
                _state.value = _state.value.copy(
                    startTime = data.time
                )
            }
            is CreateState.OnImageSelected -> {
                _state.value = _state.value.copy(
                    coverImage = data.uri
                )
            }
            is CreateState.OnCreate -> {
                createEvent()
            }
        }
    }

    private fun createEvent() {
        viewModelScope.launch {
            _createButtonState.value = true
            val result = useCase.createEvent(state.value)
            if (result.isSuccess.not()) {
                _createButtonState.value = false
                _uiState.emit(UiEvent.ShowSnackbar(result.message))
            } else {
                _createButtonState.value = false
                _uiState.emit(UiEvent.CleatBackStack)
            }
        }
    }

    private fun updateDescription(description: String) {
        _state.value = _state.value.copy(
            description = _state.value.description.copy(
                text = description
            )
        )
    }

    private fun updateTitle(title: String) {
        _state.value = _state.value.copy(
            title = _state.value.title.copy(
                text = title
            )
        )
    }
}