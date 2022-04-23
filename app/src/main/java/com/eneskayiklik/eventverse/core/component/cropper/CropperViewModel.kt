package com.eneskayiklik.eventverse.core.component.cropper

import androidx.lifecycle.ViewModel
import com.eneskayiklik.eventverse.core.data.event.CropperEvent
import com.eneskayiklik.eventverse.core.data.state.CropperState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CropperViewModel @Inject constructor(

): ViewModel() {
    private val _cropperState = MutableStateFlow(CropperState())
    val cropperState: StateFlow<CropperState> = _cropperState

    fun onEvent(event: CropperEvent) {
        when (event) {
            is CropperEvent.OnCroppingEvent -> _cropperState.value = _cropperState.value.copy(
                isCropping = event.isCropping
            )
            CropperEvent.OnRotation -> _cropperState.value = _cropperState.value.copy(
                rotation = _cropperState.value.rotation + 90
            )
            else -> Unit
        }
    }
}