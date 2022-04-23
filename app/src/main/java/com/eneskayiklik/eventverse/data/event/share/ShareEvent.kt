package com.eneskayiklik.eventverse.data.event.share

import android.net.Uri

sealed class ShareEvent {
    data class OnImage(val uri: Uri): ShareEvent()
    data class OnBody(val text: String): ShareEvent()
}
