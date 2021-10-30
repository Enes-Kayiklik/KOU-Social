package com.eneskayiklik.eventverse.feature_create.data.repository

import com.eneskayiklik.eventverse.feature_create.domain.CreateResult
import com.eneskayiklik.eventverse.feature_create.domain.model.CreateEventModel
import com.eneskayiklik.eventverse.feature_create.domain.repository.CreateRepository
import kotlinx.coroutines.delay

class CreateEventRepositoryImpl : CreateRepository {
    override suspend fun createEvent(event: CreateEventModel): CreateResult {
        delay(4000L)
        return CreateResult(isSuccess = true)
    }
}