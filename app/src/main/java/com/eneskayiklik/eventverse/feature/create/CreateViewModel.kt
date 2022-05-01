package com.eneskayiklik.eventverse.feature.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.util.UiEvent
import com.eneskayiklik.eventverse.data.repository.create.CreateEventUseCase
import com.eneskayiklik.eventverse.feature.create.util.CreateSectionState
import com.eneskayiklik.eventverse.feature.create.util.CreateState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
            is CreateState.OnDescription -> _state.value = _state.value.copy(
                description = _state.value.description.copy(
                    text = data.description,
                    error = ""
                )
            )
            is CreateState.OnTitle -> _state.value = _state.value.copy(
                title = _state.value.title.copy(
                    text = data.title,
                    error = ""
                )
            )
            is CreateState.OnLocation -> _state.value = _state.value.copy(
                location = _state.value.location.copy(
                    text = data.location,
                    error = ""
                )
            )
            is CreateState.OnEndDate -> _state.value = _state.value.copy(
                endDate = data.date
            )
            is CreateState.OnStartDate -> _state.value = _state.value.copy(
                startDate = data.date
            )
            is CreateState.OnEndTime -> _state.value = _state.value.copy(
                endTime = data.time
            )
            is CreateState.OnStartTime -> _state.value = _state.value.copy(
                startTime = data.time
            )
            is CreateState.OnImageSelected -> _state.value = _state.value.copy(
                coverImage = data.uri
            )
            is CreateState.OnCreate -> {
                createEvent()
            }
        }
    }

    private fun createEvent() = viewModelScope.launch(Dispatchers.IO) {
        val state = _state.value
        when {
            state.title.text.count() < 3 -> {
                _state.value = _state.value.copy(
                    title = _state.value.title.copy(
                        error = "Title length must be greater than 3"
                    )
                )
            }
            state.description.text.count() < 50 -> {
                _state.value = _state.value.copy(
                    description = _state.value.description.copy(
                        error = "Description must contains at least 50 character"
                    )
                )
            }
            state.location.text.count() < 10 -> {
                _state.value = _state.value.copy(
                    location = _state.value.location.copy(
                        error = "Provide more details about event location"
                    )
                )
            }
            else -> {
                _createButtonState.value = true
                val result = useCase.createEvent(state)
                if (result.isSuccess.not()) {
                    _createButtonState.value = false
                    _uiState.emit(UiEvent.ShowSnackbar(result.message))
                } else {
                    _createButtonState.value = false
                    _uiState.emit(UiEvent.ClearBackStack)
                }
            }
        }
    }
}