package com.eneskayiklik.eventverse.feature_create.presentation.component

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

@ExperimentalAnimationApi
@Composable
fun AboutEventSection(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    attendee: String,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onAttendeeChange: (String) -> Unit,
) {
    Column(modifier = modifier) {
        SectionTitle(title = stringResource(id = R.string.event_name))
        Spacer(modifier = Modifier.height(5.dp))
        ExtendedTextField(
            text = title,
            onValueChange = onTitleChange,
            // error = password.error,
            placeholder = stringResource(id = R.string.event_name),
            keyboardType = KeyboardType.Text,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        SectionTitle(title = stringResource(id = R.string.description_short))
        Spacer(modifier = Modifier.height(5.dp))
        ExtendedTextField(
            text = description,
            onValueChange = onDescriptionChange,
            // error = password.error,
            placeholder = stringResource(id = R.string.description),
            keyboardType = KeyboardType.Text,
            singleLine = false,
            maxLines = Int.MAX_VALUE,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        SectionTitle(title = stringResource(id = R.string.attendee_count))
        Spacer(modifier = Modifier.height(5.dp))
        ExtendedTextField(
            text = attendee,
            onValueChange = onAttendeeChange,
            // error = password.error,
            placeholder = stringResource(id = R.string.max_attendee_count),
            keyboardType = KeyboardType.Number,
            maxLines = 1,
            onValueChangeLogic = { it.count() <= 3 && (it.isEmpty() || it.last().isDigit()) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}