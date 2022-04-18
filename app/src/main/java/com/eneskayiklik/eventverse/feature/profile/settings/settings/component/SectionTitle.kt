package com.eneskayiklik.eventverse.feature.profile.settings.settings.component

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

fun LazyListScope.sectionTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    item {
        Text(
            text = title,
            style = MaterialTheme.typography.h1.copy(
                color = MaterialTheme.colors.onBackground,
                fontSize = 24.sp
            ), modifier = modifier
        )
    }
}