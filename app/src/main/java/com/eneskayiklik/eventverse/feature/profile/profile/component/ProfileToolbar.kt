package com.eneskayiklik.eventverse.feature.profile.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
                tint = MaterialTheme.colors.onBackground
            )
        }

        Text(
            text = stringResource(id = R.string.profile),
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

        Divider(
            modifier = Modifier
                .height(1.dp)
                .background(MaterialTheme.colors.secondary)
                .align(Alignment.BottomCenter)
        )
    }
}