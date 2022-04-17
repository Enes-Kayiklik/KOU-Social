package com.eneskayiklik.eventverse.feature_polls.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.core.component.ExtendedTextField

@Composable
fun OneRowPollOption(
    text: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    showPlusSign: Boolean = false,
    onEndIconClick: (isAdd: Boolean) -> Unit,
    onTextChanged: (String) -> Unit,
) {
    val rotateAnim = animateFloatAsState(targetValue = if (showPlusSign) -45F else 0F).value
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        isVisible = isVisible.not()
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInHorizontally(),
        exit = slideOutHorizontally()
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            ExtendedTextField(
                text = text,
                onValueChange = onTextChanged,
                error = "",
                placeholder = placeholder,
                keyboardType = KeyboardType.Text,
                modifier = Modifier.weight(9F)
            )
            IconButton(onClick = { onEndIconClick(showPlusSign) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = null,
                    modifier = Modifier
                        .rotate(rotateAnim)
                        .weight(1F),
                    tint = if (showPlusSign) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary
                )
            }
        }
    }
}