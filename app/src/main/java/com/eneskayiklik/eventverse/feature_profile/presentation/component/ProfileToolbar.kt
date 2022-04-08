package com.eneskayiklik.eventverse.feature_profile.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.eneskayiklik.eventverse.R

@Composable
fun ProfileToolbar(
    modifier: Modifier = Modifier,
    showEndIcon: Boolean = true,
    onStartIconClick: () -> Unit = { },
    onEndIconClick: () -> Unit = { },
) {
    Box(modifier = modifier) {
        IconButton(onClick = onStartIconClick, modifier = Modifier.align(Alignment.CenterStart)) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = MaterialTheme.colors.onBackground
            )
        }

        Text(
            text = "Profile",
            style = MaterialTheme.typography.h3.copy(
                color = MaterialTheme.colors.onBackground,
                fontSize = 20.sp
            ), modifier = Modifier.align(Alignment.Center)
        )
        if (showEndIcon)
            IconButton(onClick = onEndIconClick, modifier = Modifier.align(Alignment.CenterEnd)) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_settings),
                    contentDescription = null,
                    tint = MaterialTheme.colors.onBackground
                )
            }
    }
}