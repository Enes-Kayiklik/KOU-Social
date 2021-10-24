package com.eneskayiklik.eventverse.feature_auth.presentation.interest.util

sealed class SelectInterestEvent {
    object OnSelectInterest : SelectInterestEvent()
    data class SelectInterestResult(val isSuccess: Boolean, val message: String = "") :
        SelectInterestEvent()
}
