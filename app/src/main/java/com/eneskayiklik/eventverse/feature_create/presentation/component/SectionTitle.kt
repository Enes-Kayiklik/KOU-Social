package com.eneskayiklik.eventverse.feature_create.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun SectionTitle(
    modifier: Modifier = Modifier,
    title: String,
    endActionTitle: String? = null,
    onEndAction: () -> Unit = { }
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, style = MaterialTheme.typography.h1.copy(fontSize = 14.sp))
        if (endActionTitle != null) {
            Text(
                text = endActionTitle,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.clickable {
                    onEndAction()
                })
        }
    }
}