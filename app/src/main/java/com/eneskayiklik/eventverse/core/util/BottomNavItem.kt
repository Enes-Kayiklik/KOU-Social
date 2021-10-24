package com.eneskayiklik.eventverse.core.util

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val route: String = "",
    val icon: ImageVector? = null,
    val contentDescription: String = ""
)
