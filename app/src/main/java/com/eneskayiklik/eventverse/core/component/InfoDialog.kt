package com.eneskayiklik.eventverse.core.component

import com.eneskayiklik.eventverse.core.model.ErrorState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun InfoDialog(state: ErrorState, onDismissRequest: () -> Unit = { }) {
    var isAnimPlayed by remember { mutableStateOf(false) }
    val initialAnim by animateFloatAsState(
        targetValue = if (isAnimPlayed.not()) 0.90F else 1F,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    LaunchedEffect(key1 = Unit) {
        isAnimPlayed = true
    }
    Dialog(
        onDismissRequest = onDismissRequest,
        DialogProperties(
            dismissOnBackPress = state.dismissOnBackPressed,
            dismissOnClickOutside = state.dismissOnOutClick
        )
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .scale(initialAnim)
                .background(MaterialTheme.colors.background, shape = MaterialTheme.shapes.medium)
                .padding(15.dp)
        ) {
            if (state.title != null)
                Text(
                    text = state.title,
                    style = MaterialTheme.typography.h1.copy(
                        fontSize = 28.sp,
                        color = MaterialTheme.colors.onBackground
                    )
                )
            if (state.subTitle != null)
                Text(
                    text = state.subTitle,
                    style = MaterialTheme.typography.h2.copy(
                        fontSize = 16.sp,
                        color = MaterialTheme.colors.onSurface,
                        lineHeight = 20.sp
                    ), modifier = Modifier.padding(end = 16.dp)
                )

            Spacer(modifier = Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                if (state.firstButtonText != null)
                    DialogButton(
                        modifier = Modifier.weight(1F),
                        text = state.firstButtonText,
                        fontColor = MaterialTheme.colors.onSurface,
                        backgroundColor = MaterialTheme.colors.surface,
                        onClick = state.firstButtonClick
                    )
                if (state.secondButtonText != null)
                    DialogButton(
                        modifier = Modifier.weight(1F),
                        text = state.secondButtonText,
                        fontColor = Color.White,
                        backgroundColor = MaterialTheme.colors.primary,
                        onClick = state.secondButtonClick
                    )
            }
        }
    }
}

@Composable
fun DialogButton(
    text: String,
    fontColor: Color,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(44.dp),
        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp, pressedElevation = 2.dp),
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.h4.copy(fontSize = 16.sp, color = fontColor)
        )
    }
}