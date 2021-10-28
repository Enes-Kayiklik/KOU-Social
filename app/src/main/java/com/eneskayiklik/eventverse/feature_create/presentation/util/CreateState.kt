package com.eneskayiklik.eventverse.feature_create.presentation.util

sealed class CreateState {
    data class OnTitle(val title: String) : CreateState()
    data class OnDescription(val description: String) : CreateState()
}
