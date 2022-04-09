package com.eneskayiklik.eventverse.feature_settings.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.eneskayiklik.eventverse.R

@Composable
fun SettingsToolbar(
    modifier: Modifier = Modifier,
    onStartIconClick: () -> Unit = { },
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
            text = stringResource(id = R.string.settings),
            style = MaterialTheme.typography.h3.copy(
                color = MaterialTheme.colors.onBackground,
                fontSize = 20.sp
            ), modifier = Modifier.align(Alignment.Center)
        )
    }
}