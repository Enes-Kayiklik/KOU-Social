package com.eneskayiklik.eventverse.feature_create.presentation.util

import android.net.Uri
import com.eneskayiklik.eventverse.core.util.TextFieldState
import com.eneskayiklik.eventverse.feature_create.domain.model.CreateEventModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

data class CreateSectionState(
    val title: TextFieldState = TextFieldState(),
    val description: TextFieldState = TextFieldState(),
    val attendee: TextFieldState = TextFieldState(),
    val startDate: LocalDate = LocalDate.now(),
    val endDate: LocalDate = LocalDate.now().plusDays(1),
    val startTime: LocalTime = LocalTime.now(),
    val endTime: LocalTime = LocalTime.now(),
    val coverImage: Uri? = null,
    val selectedLocation: String = "Istanbul"
) {
    val formattedStartDate: String = startDate.format(
        DateTimeFormatter.ofPattern("dd.MM.yyyy")
    )
    val formattedStartHour: String = startTime.format(
        DateTimeFormatter.ofPattern("HH:mm")
    )
    val formattedEndDate: String = endDate.format(
        DateTimeFormatter.ofPattern("dd.MM.yyyy")
    )
    val formattedEndHour: String = endTime.format(
        DateTimeFormatter.ofPattern("HH:mm")
    )
    val startTimeLong: Long = LocalDateTime.of(startDate, startTime)
        .toEpochSecond(ZoneOffset.UTC)
    val endTimeLong: Long = LocalDateTime.of(endDate, endTime)
        .toEpochSecond(ZoneOffset.UTC)
}

fun CreateSectionState.toCreateEventModel(): CreateEventModel {
    return CreateEventModel(
        title = title.text,
        description = description.text,
        startTimeLong * 1000,
        endTimeLong * 1000,
        "",
        ""
    )
}