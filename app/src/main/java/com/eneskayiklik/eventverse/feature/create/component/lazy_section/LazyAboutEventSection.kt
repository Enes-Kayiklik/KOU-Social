package com.eneskayiklik.eventverse.feature.create.component.lazy_section

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.feature.create.component.AboutEventSection
import com.eneskayiklik.eventverse.feature.create.component.HeaderSection
import com.eneskayiklik.eventverse.feature.create.util.CreateSectionState
import com.eneskayiklik.eventverse.feature.create.util.CreateState

@ExperimentalAnimationApi
@ExperimentalFoundationApi
fun LazyListScope.aboutEventSection(
    state: CreateSectionState,
    onCreateState: (CreateState) -> Unit
) {
    item {
        HeaderSection(
            title = stringResource(id = R.string.about_event),
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface)
                .padding(16.dp)
        )
        Divider(color = MaterialTheme.colors.background, thickness = 2.dp)
    }

    item {
        AboutEventSection(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .padding(horizontal = 16.dp, vertical = 16.dp),
            title = state.title.text,
            description = state.description.text,
            attendee = state.attendee.text,
            onAttendeeChange = { onCreateState(CreateState.OnAttendee(it)) },
            onTitleChange = { onCreateState(CreateState.OnTitle(it)) },
            onDescriptionChange = { onCreateState(CreateState.OnDescription(it)) }
        )
    }
}