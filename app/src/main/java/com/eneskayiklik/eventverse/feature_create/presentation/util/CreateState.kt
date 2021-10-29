package com.eneskayiklik.eventverse.feature_create.presentation.util

import java.time.LocalDate
import java.time.LocalTime

sealed class CreateState {
    data class OnTitle(val title: String) : CreateState()
    data class OnDescription(val description: String) : CreateState()
    data class OnStartDate(val date: LocalDate) : CreateState()
    data class OnStartTime(val time: LocalTime) : CreateState()
    data class OnEndDate(val date: LocalDate) : CreateState()
    data class OnEndTime(val time: LocalTime) : CreateState()
}
