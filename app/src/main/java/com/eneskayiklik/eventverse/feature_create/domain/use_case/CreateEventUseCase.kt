package com.eneskayiklik.eventverse.feature_create.domain.use_case

import com.eneskayiklik.eventverse.feature_create.domain.CreateResult
import com.eneskayiklik.eventverse.feature_create.domain.repository.CreateRepository
import com.eneskayiklik.eventverse.feature_create.presentation.util.CreateSectionState
import com.eneskayiklik.eventverse.feature_create.presentation.util.toCreateEventModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import javax.inject.Inject

class CreateEventUseCase @Inject constructor(
    private val createRepository: CreateRepository
) {
    suspend fun createEvent(
        event: CreateSectionState
    ): CreateResult {
        if (event.title.text.isEmpty())
            return CreateResult(message = "Title should not be empty.")
        val timeCheck = calculateValidDate(event.startTimeLong, event.endTimeLong)
        return timeCheck ?: createRepository.createEvent(event.toCreateEventModel())
    }

    private fun calculateValidDate(startDate: Long, endDate: Long): CreateResult? {
        val currentSecond = LocalDateTime.of(LocalDate.now(), LocalTime.now())
            .toEpochSecond(ZoneOffset.UTC)
        return when {
            startDate > endDate -> CreateResult(message = "Start date can't be before from end date.")
            startDate - currentSecond < 12 * 60 * 60 -> CreateResult(message = "Event must start at least 12 hours earlier.")
            endDate - startDate < 60 * 60 -> CreateResult(message = "Event time must be at least 1 hour.")
            else -> null
        }
    }
}