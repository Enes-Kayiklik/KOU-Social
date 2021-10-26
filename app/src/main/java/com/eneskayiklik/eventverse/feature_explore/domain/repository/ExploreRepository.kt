package com.eneskayiklik.eventverse.feature_explore.domain.repository

import com.eneskayiklik.eventverse.feature_explore.domain.model.UpcomingEventModel

interface ExploreRepository {
    suspend fun getUpcomingEvents(): List<UpcomingEventModel>
}