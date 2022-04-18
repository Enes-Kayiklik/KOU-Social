package com.eneskayiklik.eventverse.core.component

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.core.ui.theme.White
import com.eneskayiklik.eventverse.util.Screen

@Composable
fun MainFloatingActionGroup(
    isClicked: Boolean,
    onFabClick: () -> Unit,
    onNavigate: (String) -> Unit
) {
    // Close fab on back pressed
    BackHandler(isClicked, onFabClick)

    val firstTranslationY by animateDpAsState(targetValue = if (isClicked) (-60).dp else 0.dp)
    val secondTranslationY by animateDpAsState(targetValue = if (isClicked) (-110).dp else 0.dp)
    var isTextVisible by remember { mutableStateOf(isClicked) }
    val rotation by animateFloatAsState(targetValue = if (isClicked) 45F else 0F) {
        isTextVisible = isTextVisible.not()
    }

    Box(contentAlignment = Alignment.CenterEnd) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .graphicsLayer {
                    translationY = firstTranslationY.toPx()
                    translationX = -8.dp.toPx()
                },
        ) {
            AnimatedVisibility(visible = isTextVisible) {
                Text(
                    text = stringResource(id = R.string.create_poll),
                    style = MaterialTheme.typography.h1.copy(
                        color = MaterialTheme.colors.onBackground, fontSize = 14.sp
                    )
                )
            }
            FloatingActionButton(modifier = Modifier
                .size(40.dp), onClick = {
                onNavigate(Screen.CreatePoll.route)
            }) {
                Image(imageVector = Icons.Default.Edit, contentDescription = "Shuffle")
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .graphicsLayer {
                    translationY = secondTranslationY.toPx()
                    translationX = -8.dp.toPx()
                },
        ) {
            AnimatedVisibility(visible = isTextVisible) {
                Text(
                    text = stringResource(id = R.string.create_post),
                    style = MaterialTheme.typography.h1.copy(
                        color = MaterialTheme.colors.onBackground, fontSize = 14.sp
                    )
                )
            }
            FloatingActionButton(modifier = Modifier
                .size(40.dp), onClick = {
                onNavigate(Screen.Share.route)
            }) {
                Image(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
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