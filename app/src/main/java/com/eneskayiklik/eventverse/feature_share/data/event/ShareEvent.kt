package com.eneskayiklik.eventverse.feature_share.data.event

sealed class ShareEvent {
    data class OnTitle(val text: String): ShareEvent()
    data class OnBody(val text: String): ShareEvent()
}
