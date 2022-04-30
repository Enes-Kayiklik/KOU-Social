package com.eneskayiklik.eventverse.core.component

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.core.ui.theme.White
import com.eneskayiklik.eventverse.util.Screen
import com.eneskayiklik.eventverse.util.Settings

@Composable
fun MainFloatingActionGroup(
    isClicked: Boolean,
    onFabClick: () -> Unit,
    onNavigate: (String) -> Unit
) {
    // Close fab on back pressed
    BackHandler(isClicked, onFabClick)

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp * -1
    val translationValue =
        animateDpAsState(targetValue = if (isClicked) screenWidth else 0.dp).value
    var isTextVisible by remember { mutableStateOf(isClicked) }
    val rotation by animateFloatAsState(targetValue = if (isClicked) 45F else 0F) {
        isTextVisible = isTextVisible.not()
    }

    Box(contentAlignment = Alignment.Center) {
        FloatingActionButton(modifier = Modifier
            .graphicsLayer {
                translationY = translationValue.value / 2
            }
            .size(40.dp), onClick = {
            onNavigate(Screen.CreatePoll.route)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_poll),
                contentDescription = null,
                tint = MaterialTheme.colors.onSecondary
            )
        }

        FloatingActionButton(modifier = Modifier
            .graphicsLayer {
                translationX = translationValue.value / 3
                translationY = translationValue.value / 3
            }
            .size(40.dp), onClick = {
            onNavigate(Screen.Share.route)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_reviews),
                contentDescription = null,
                tint = MaterialTheme.colors.onSecondary
            )
        }

        if (Settings.currentUser.verified)
            FloatingActionButton(modifier = Modifier
                .graphicsLayer {
                    translationX = translationValue.value / 2
                }
                .size(40.dp), onClick = {
                onNavigate(Screen.Share.route)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_ticket),
                    contentDescription = null,
                    tint = MaterialTheme.colors.onSecondary
                )
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