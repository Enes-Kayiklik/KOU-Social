package com.eneskayiklik.eventverse.feature.create.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.core.component.ExtendedTextField
import com.eneskayiklik.eventverse.feature.create.util.CreateSectionState

@ExperimentalAnimationApi
@Composable
fun AboutEventSection(
    modifier: Modifier = Modifier,
    state: CreateSectionState,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onLocationChange: (String) -> Unit,
) {
    Column(modifier = modifier) {
        ExtendedTextField(
            text = state.title.text,
            error = state.title.error,
            onValueChange = onTitleChange,
            label = stringResource(id = R.string.event_name),
            placeholder = stringResource(id = R.string.event_name),
            keyboardType = KeyboardType.Text,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        ExtendedTextField(
            text = state.description.text,
            onValueChange = onDescriptionChange,
            label = stringResource(id = R.string.description_short),
            error = state.description.error,
            placeholder = stringResource(id = R.string.description),
            keyboardType = KeyboardType.Text,
            singleLine = false,
            maxLines = Int.MAX_VALUE,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        ExtendedTextField(
            text = state.location.text,
            onValueChange = onLocationChange,
            error = state.location.error,
            label = stringResource(id = R.string.location),
            placeholder = stringResource(id = R.string.location_placeholder),
            keyboardType = KeyboardType.Text,
            maxLines = 2,
            modifier = Modifier.fillMaxWidth()
        )
    }
}