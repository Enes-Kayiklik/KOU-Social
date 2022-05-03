package com.eneskayiklik.eventverse.feature.events.detail.lazy_items.tab

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@Composable
fun DetailPage(
    desc: String
) {
    Text(
        text = desc, style = MaterialTheme.typography.h4.copy(
            color = MaterialTheme.colors.onBackground,
            fontSize = 16.sp
        )
    )
}