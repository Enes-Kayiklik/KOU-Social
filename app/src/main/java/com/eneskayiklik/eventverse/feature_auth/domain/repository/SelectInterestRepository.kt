package com.eneskayiklik.eventverse.feature_auth.domain.repository

import com.eneskayiklik.eventverse.feature_auth.domain.model.InterestModel

interface SelectInterestRepository {
    suspend fun getInterests(): List<InterestModel>
}