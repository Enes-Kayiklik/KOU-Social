package com.eneskayiklik.eventverse.core.component

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.core.ui.theme.White

@Composable
fun MainFloatingActionGroup(
    isClicked: Boolean,
    onFabClick: () -> Unit
) {
    // Close fab on back pressed
    BackHandler(isClicked, onFabClick)

    val rotation by animateFloatAsState(targetValue = if (isClicked) 180F else 0F)
    val firstTranslationY by animateDpAsState(targetValue = if (isClicked) (-50).dp else 0.dp)
    val secondTranslationY by animateDpAsState(targetValue = if (isClicked) (-90).dp else 0.dp)
    Box(contentAlignment = Alignment.Center) {
        FloatingActionButton(modifier = Modifier
            .size(30.dp)
            .graphicsLayer {
                translationY = firstTranslationY.toPx()
            }, onClick = { }) {
            Image(imageVector = Icons.Default.Edit, contentDescription = "Shuffle")
        }
        FloatingActionButton(modifier = Modifier
            .size(30.dp)
            .graphicsLayer {
                translationY = secondTranslationY.toPx()
            }, onClick = { }) {
            Image(imageVector = Icons.Default.Add, contentDescription = "Add")
        }
        FloatingActionButton(
            backgroundColor = MaterialTheme.colors.primary,
            onClick = onFabClick,
            modifier = Modifier.rotate(rotation)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                tint = White,
                contentDescription = stringResource(id = R.string.create_event)
            )
        }

    }
}