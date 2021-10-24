package com.eneskayiklik.eventverse.feature_auth.domain.use_case

import com.eneskayiklik.eventverse.feature_auth.domain.model.InterestModel
import com.eneskayiklik.eventverse.feature_auth.domain.repository.SelectInterestRepository
import com.eneskayiklik.eventverse.feature_auth.presentation.interest.util.SelectInterestEvent
import kotlinx.coroutines.delay
import javax.inject.Inject

class SelectInterestUseCase @Inject constructor(
    private val interestRepo: SelectInterestRepository
) {
    suspend fun getInterests() = interestRepo.getInterests()

    suspend fun setInterests(interests: List<InterestModel>): SelectInterestEvent {
        val selected = interests.filter { it.isSelected }.map { it.id }
        return if (selected.isEmpty()) {
            SelectInterestEvent.SelectInterestResult(
                false, "You should select at least 1 interest."
            )
        } else {
            delay(3000L)
            SelectInterestEvent.SelectInterestResult(true)
        }
    }
}