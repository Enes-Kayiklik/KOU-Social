package com.eneskayiklik.eventverse.feature_create.presentation

import androidx.lifecycle.ViewModel
import com.eneskayiklik.eventverse.feature_create.presentation.util.CreateSectionState
import com.eneskayiklik.eventverse.feature_create.presentation.util.CreateState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(

) : ViewModel() {
    private val _state = MutableStateFlow(CreateSectionState())
    val state: StateFlow<CreateSectionState> = _state

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