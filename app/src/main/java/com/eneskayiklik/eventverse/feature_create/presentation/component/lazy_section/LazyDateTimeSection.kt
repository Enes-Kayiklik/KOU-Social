package com.eneskayiklik.eventverse.feature_create.presentation.component.lazy_section

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Timelapse
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.feature_create.presentation.component.HeaderSection
import com.eneskayiklik.eventverse.feature_create.presentation.component.date_time.TimeSelectionItem

@ExperimentalMaterialApi
@ExperimentalFoundationApi
fun LazyListScope.dateTimeSection() {
    stickyHeader {
        HeaderSection(
            title = stringResource(id = R.string.date_and_time),
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface)
                .padding(16.dp)
        )
        Divider(color = MaterialTheme.colors.background, thickness = 2.dp)
    }

    item {
        DateSection()
    }
}

@ExperimentalMaterialApi
@Composable
private fun DateSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TimeSelectionItem(
                modifier = Modifier.weight(1F),
                headerText = "Start Date",
                text = "10.08.2021",
                Icons.Rounded.DateRange
            ) {

            }
            TimeSelectionItem(
                modifier = Modifier.weight(1F),
                headerText = "Start Time",
                text = "09:00",
                Icons.Rounded.Timelapse
            ) {

            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TimeSelectionItem(
                modifier = Modifier.weight(1F),
                headerText = "End Date",
                text = "10.08.2021",
                Icons.Rounded.DateRange
            ) {

            }
            TimeSelectionItem(
                modifier = Modifier.weight(1F),
                headerText = "End Time",
                text = "09:00",
                Icons.Rounded.Timelapse
            ) {

            }
        }
    }
}