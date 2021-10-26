package com.eneskayiklik.eventverse.feature_explore.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.core.ui.theme.White

@Composable
fun EventverseAppbar(
    modifier: Modifier = Modifier,
    startIcon: ImageVector? = null,
    endIcon: Painter? = null,
    title: String = stringResource(id = R.string.app_name).lowercase(),
    onStartIconClick: () -> Unit = { },
    onEndIconClick: () -> Unit = { },
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (startIcon != null) {
            IconButton(onClick = onStartIconClick) {
                Icon(
                    imageVector = startIcon,
                    tint = White,
                    contentDescription = stringResource(id = R.string.settings)
                )
            }
        }
        Text(
            text = title,
            color = White,
            style = MaterialTheme.typography.h1.copy(fontSize = 20.sp)
        )
        if (endIcon != null) {
            IconButton(onClick = onEndIconClick) {
                Icon(
                    painter = endIcon,
                    tint = White,
                    contentDescription = stringResource(id = R.string.settings)
                )
            }
        }
    }
}