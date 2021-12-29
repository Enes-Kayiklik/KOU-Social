package com.eneskayiklik.eventverse.core.util

sealed class UiEvent {
    data class Navigate(val id: String) : UiEvent()
    data class ShowSnackbar(val message: String) : UiEvent()
    object ClearBackStack : UiEvent()
}
