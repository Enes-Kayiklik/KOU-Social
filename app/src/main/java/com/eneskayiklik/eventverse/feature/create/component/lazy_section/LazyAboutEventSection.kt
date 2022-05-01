package com.eneskayiklik.eventverse.feature.create.component.lazy_section

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eneskayiklik.eventverse.feature.create.component.AboutEventSection
import com.eneskayiklik.eventverse.feature.create.util.CreateSectionState
import com.eneskayiklik.eventverse.feature.create.util.CreateState

@ExperimentalAnimationApi
@ExperimentalFoundationApi
fun LazyListScope.aboutEventSection(
    state: CreateSectionState,
    onCreateState: (CreateState) -> Unit
) {
    item {
        AboutEventSection(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp),
            state = state,
            onLocationChange = { onCreateState(CreateState.OnLocation(it)) },
            onTitleChange = { onCreateState(CreateState.OnTitle(it)) },
            onDescriptionChange = { onCreateState(CreateState.OnDescription(it)) }
        )
    }
}