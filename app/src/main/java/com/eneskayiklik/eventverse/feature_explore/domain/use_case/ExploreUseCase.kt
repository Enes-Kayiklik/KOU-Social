package com.eneskayiklik.eventverse.feature_explore.domain.use_case

import com.eneskayiklik.eventverse.feature_explore.domain.model.ExploreEventModel
import com.eneskayiklik.eventverse.feature_explore.domain.repository.ExploreRepository
import javax.inject.Inject

class ExploreUseCase @Inject constructor(
    private val exploreRepo: ExploreRepository
) {
    suspend fun getUpcomingEvents(): List<ExploreEventModel> {
        return exploreRepo.getUpcomingEvents()
    }
}