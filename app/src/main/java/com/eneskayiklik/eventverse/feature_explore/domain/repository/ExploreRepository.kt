package com.eneskayiklik.eventverse.feature_explore.domain.repository

import com.eneskayiklik.eventverse.feature_explore.domain.model.ExploreEventModel

interface ExploreRepository {
    suspend fun getUpcomingEvents(): List<ExploreEventModel>
}