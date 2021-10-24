package com.eneskayiklik.eventverse.feature_auth.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

data class InterestModel(
    val id: Int = 0,
    val title: String = "",
    val icon: ImageVector,
    var isSelected: Boolean = false
)
