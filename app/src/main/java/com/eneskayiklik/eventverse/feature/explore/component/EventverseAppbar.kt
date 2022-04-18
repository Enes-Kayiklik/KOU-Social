package com.eneskayiklik.eventverse.feature.explore.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.core.ui.theme.White

@Composable
fun EventverseAppbar(
    modifier: Modifier = Modifier,
    startIcon: ImageVector? = null,
    endIcon: ImageVector? = null,
    title: String = stringResource(id = R.string.app_name),
    onStartIconClick: () -> Unit = { },
    onEndIconClick: () -> Unit = { },
) {
    Box(modifier = modifier) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = title,
            color = White,
            style = MaterialTheme.typography.h1.copy(fontSize = 20.sp),
        )
        if (startIcon != null) {
            IconButton(
                modifier = Modifier.align(Alignment.CenterStart),
                onClick = onStartIconClick
            ) {
                Icon(
                    imageVector = startIcon,
                    tint = Color.White,
                    contentDescription = stringResource(id = R.string.settings)
                )
            }
        }
        if (endIcon != null) {
            IconButton(modifier = Modifier.align(Alignment.CenterEnd), onClick = onEndIconClick) {
                Icon(
                    imageVector = endIcon,
                    tint = Color.White,
                    contentDescription = stringResource(id = R.string.settings)
                )
            }
        }
    }
}