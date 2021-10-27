package com.eneskayiklik.eventverse.feature_explore.presentation.component.select_location

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eneskayiklik.eventverse.R

@ExperimentalMaterialApi
@Composable
fun SelectLocationSection(
    modifier: Modifier = Modifier,
    selectedLocation: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier,
        onClick = onClick
    ) {
        Row(
            modifier = modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.LocationOn,
                    contentDescription = stringResource(id = R.string.location),
                    tint = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = selectedLocation,
                    style = MaterialTheme.typography.h1.copy(fontSize = 18.sp),
                    color = MaterialTheme.colors.onSurface
                )
            }

            Text(
                text = stringResource(id = R.string.change_location),
                style = MaterialTheme.typography.h1.copy(fontSize = 14.sp),
                color = MaterialTheme.colors.primary
            )
        }
    }
}