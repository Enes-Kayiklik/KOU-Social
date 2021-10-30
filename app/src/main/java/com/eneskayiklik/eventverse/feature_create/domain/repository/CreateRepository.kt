package com.eneskayiklik.eventverse.feature_create.domain.repository

import com.eneskayiklik.eventverse.feature_create.domain.CreateResult
import com.eneskayiklik.eventverse.feature_create.domain.model.CreateEventModel

interface CreateRepository {
    suspend fun createEvent(event: CreateEventModel): CreateResult
}