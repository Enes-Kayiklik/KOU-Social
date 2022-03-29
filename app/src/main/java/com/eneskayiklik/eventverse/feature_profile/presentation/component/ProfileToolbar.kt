package com.eneskayiklik.eventverse.feature_profile.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun ProfileToolbar(
    modifier: Modifier = Modifier,
    onStartIconClick: () -> Unit = { },
    onEndIconClick: () -> Unit = { },
) {
    Box(modifier = modifier) {
        IconButton(onClick = onStartIconClick, modifier = Modifier.align(Alignment.CenterStart)) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
        }

        Text(
            text = "Profile",
            style = MaterialTheme.typography.h1.copy(
                color = MaterialTheme.colors.onBackground,
                fontSize = 20.sp
            ), modifier = Modifier.align(Alignment.Center)
        )

        IconButton(onClick = onEndIconClick, modifier = Modifier.align(Alignment.CenterEnd)) {
            Icon(imageVector = Icons.Default.Settings, contentDescription = null)
        }
    }
}