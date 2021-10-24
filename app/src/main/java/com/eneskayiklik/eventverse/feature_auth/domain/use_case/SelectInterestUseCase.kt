package com.eneskayiklik.eventverse.feature_auth.domain.use_case

import com.eneskayiklik.eventverse.feature_auth.domain.model.InterestModel
import com.eneskayiklik.eventverse.feature_auth.domain.repository.SelectInterestRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class SelectInterestUseCase @Inject constructor(
    private val interestRepo: SelectInterestRepository
) {
    suspend fun getInterests() = interestRepo.getInterests()

    suspend fun setInterests(interests: List<InterestModel>): Boolean {
        val selected = interests.filter { it.isSelected }.map { it.id }
        delay(3000L)
        return true
    }
}