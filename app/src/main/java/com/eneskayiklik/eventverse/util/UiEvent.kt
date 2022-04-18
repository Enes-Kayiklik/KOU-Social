package com.eneskayiklik.eventverse.util

sealed class UiEvent {
    data class Navigate(val id: String) : UiEvent()
    data class ShowSnackbar(val message: String) : UiEvent()
    data class Toast(val message: String) : UiEvent()
    object RestartApp : UiEvent()
    object ClearBackStack : UiEvent()
}
