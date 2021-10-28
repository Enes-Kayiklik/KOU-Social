package com.eneskayiklik.eventverse.feature_create.presentation

import androidx.lifecycle.ViewModel
import com.eneskayiklik.eventverse.core.util.TextFieldState
import com.eneskayiklik.eventverse.feature_create.presentation.util.CreateState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(

) : ViewModel() {
    private val _titleState = MutableStateFlow(TextFieldState())
    val titleState: StateFlow<TextFieldState> = _titleState

    private val _descriptionState = MutableStateFlow(TextFieldState())
    val descriptionState: StateFlow<TextFieldState> = _descriptionState

    fun onCreateState(
        data: CreateState
    ) {
        when (data) {
            is CreateState.OnDescription -> {
                _descriptionState.value = _descriptionState.value.copy(
                    text = data.description
                )
            }
            is CreateState.OnTitle -> {
                _titleState.value = _titleState.value.copy(
                    text = data.title
                )
            }
        }
    }
}