package com.eneskayiklik.eventverse.data.event.share

sealed class ShareEvent {
    data class OnTitle(val text: String): ShareEvent()
    data class OnBody(val text: String): ShareEvent()
}
