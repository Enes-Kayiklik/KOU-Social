package com.eneskayiklik.eventverse.data.repository.create

import com.eneskayiklik.eventverse.feature.create.util.CreateSectionState
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import javax.inject.Inject

class CreateEventUseCase @Inject constructor(
    //private val createRepository: CreateEventRepositoryImpl
) {
    suspend fun createEvent(
        event: CreateSectionState
    ): CreateResult {
        if (event.title.text.length > 3)
            return CreateResult(message = "Title length must be greater than 3.")
        val timeCheck = calculateValidDate(event.startTimeLong, event.endTimeLong)
        return timeCheck ?: CreateResult()//?: createRepository.createEvent(event.toCreateEventModel())
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