package com.eneskayiklik.eventverse.feature_explore.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.eneskayiklik.eventverse.R

@Composable
fun ExploreToolbar(
    modifier: Modifier = Modifier,
    onStartIconClick: () -> Unit = { },
    onEndIconClick: () -> Unit = { },
) {
    Box(modifier = modifier) {
        Icon(
            painter = painterResource(id = R.drawable.ic_kou),
            tint = MaterialTheme.colors.onBackground,
            modifier = Modifier.align(Alignment.Center),
            contentDescription = null
        )
    }
}