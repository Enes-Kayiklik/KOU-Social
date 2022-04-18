package com.eneskayiklik.eventverse.feature.auth.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IntroductionText(
    title: String,
    subtitle: String
) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.h1.copy(
                fontSize = 28.sp,
                color = MaterialTheme.colors.onBackground
            )
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.h2.copy(
                fontSize = 16.sp,
                color = MaterialTheme.colors.onSurface,
                lineHeight = 20.sp
            ), modifier = Modifier.padding(end = 16.dp)
        )
    }
}