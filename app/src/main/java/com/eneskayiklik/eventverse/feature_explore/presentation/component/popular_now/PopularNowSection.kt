package com.eneskayiklik.eventverse.feature_explore.presentation.component.popular_now

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.feature_explore.domain.model.ExploreEventModel
import com.eneskayiklik.eventverse.feature_explore.presentation.component.horizontal_section.HorizontalSection

@ExperimentalMaterialApi
@Composable
fun PopularNowSection(
    modifier: Modifier = Modifier,
    items: List<ExploreEventModel>,
    onItemSelected: (id: String) -> Unit
) {
    HorizontalSection(
        modifier = modifier,
        sectionTitle = stringResource(id = R.string.popular_section),
        items = items,
        onItemSelected = onItemSelected
    )
}