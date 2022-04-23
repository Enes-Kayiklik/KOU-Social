package com.eneskayiklik.eventverse.core.data.event

import android.content.Context
import android.graphics.Bitmap

sealed class CropperEvent {
    data class OnCroppingEvent(val isCropping: Boolean) : CropperEvent()
    data class OnCropFinish(val context: Context, val bitmap: Bitmap?) : CropperEvent()
    object OnCropCancel : CropperEvent()
    object OnRotation : CropperEvent()
}
