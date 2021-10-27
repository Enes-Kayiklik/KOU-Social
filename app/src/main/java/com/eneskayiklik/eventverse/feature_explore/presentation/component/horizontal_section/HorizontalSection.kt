package com.eneskayiklik.eventverse.feature_explore.presentation.component.horizontal_section

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eneskayiklik.eventverse.feature_explore.domain.model.ExploreEventModel
import com.eneskayiklik.eventverse.feature_explore.presentation.component.HeaderSection
import com.eneskayiklik.eventverse.feature_explore.presentation.component.upcoming.SingleUpcomingPage

@ExperimentalMaterialApi
@Composable
fun HorizontalSection(
    modifier: Modifier = Modifier,
    sectionTitle: String,
    items: List<ExploreEventModel>,
    onItemSelected: (id: String) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = modifier
        ) {
            HeaderSection(modifier = Modifier.padding(start = 16.dp), title = sectionTitle)
            Spacer(modifier = Modifier.height(10.dp))
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 2.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items) { data ->
                    SingleUpcomingPage(data = data, onItemClick = onItemSelected)
                }
            }
        }
    }
}