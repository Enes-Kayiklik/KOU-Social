package com.eneskayiklik.eventverse.feature.create.component.lazy_section

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.feature.create.component.HeaderSection
import com.eneskayiklik.eventverse.feature.explore.component.select_location.SelectLocationSection

@ExperimentalMaterialApi
@ExperimentalFoundationApi
fun LazyListScope.locationSection() {
    item {
        HeaderSection(
            title = stringResource(id = R.string.location_section),
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface)
                .padding(16.dp)
        )
        Divider(color = MaterialTheme.colors.background, thickness = 2.dp)
    }

    item {
        SelectLocationSection(
            modifier = Modifier.fillMaxWidth(),
            selectedLocation = "Osmaniye"
        ) {
            // TODO("select city on click")
        }
    }
}