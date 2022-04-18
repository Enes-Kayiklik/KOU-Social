package com.eneskayiklik.eventverse.feature.create.component.lazy_section

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
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.feature.create.component.HeaderSection
import com.eneskayiklik.eventverse.feature.create.component.date_time.MaterialDialogPicker
import com.eneskayiklik.eventverse.feature.create.component.date_time.TimeSelectionItem
import com.eneskayiklik.eventverse.feature.create.util.*

@ExperimentalMaterialApi
@ExperimentalFoundationApi
fun LazyListScope.dateTimeSection(
    state: CreateSectionState,
    onCreateState: (CreateState) -> Unit
) {
    item {
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
        DateSection(state, onCreateState)
    }
}

@ExperimentalMaterialApi
@Composable
private fun DateSection(
    state: CreateSectionState,
    onCreateState: (CreateState) -> Unit
) {
    val pickerState = rememberPickerState()
    var dialogType by remember { mutableStateOf(PickerType.DATE) }
    var pickerSection by remember { mutableStateOf(PickerSection.START) }

    MaterialDialogPicker(
        state = pickerState,
        dialogType, onDate = {
            when (pickerSection) {
                PickerSection.START -> {
                    onCreateState(
                        CreateState.OnStartDate(it)
                    )
                }
                PickerSection.END -> {
                    onCreateState(
                        CreateState.OnEndDate(it)
                    )
                }
            }
        }, onTime = {
            when (pickerSection) {
                PickerSection.START -> {
                    onCreateState(
                        CreateState.OnStartTime(it)
                    )
                }
                PickerSection.END -> {
                    onCreateState(
                        CreateState.OnEndTime(it)
                    )
                }
            }
        }
    )
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
                text = state.formattedStartDate,
                Icons.Rounded.DateRange
            ) {
                dialogType = PickerType.DATE
                pickerSection = PickerSection.START
                pickerState.show()
            }
            TimeSelectionItem(
                modifier = Modifier.weight(1F),
                headerText = "Start Time",
                text = state.formattedStartHour,
                Icons.Rounded.Timelapse
            ) {
                dialogType = PickerType.TIME
                pickerSection = PickerSection.START
                pickerState.show()
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
                text = state.formattedEndDate,
                Icons.Rounded.DateRange
            ) {
                dialogType = PickerType.DATE
                pickerSection = PickerSection.END
                pickerState.show()
            }
            TimeSelectionItem(
                modifier = Modifier.weight(1F),
                headerText = "End Time",
                text = state.formattedEndHour,
                Icons.Rounded.Timelapse
            ) {
                dialogType = PickerType.TIME
                pickerSection = PickerSection.END
                pickerState.show()
            }
        }
    }
}