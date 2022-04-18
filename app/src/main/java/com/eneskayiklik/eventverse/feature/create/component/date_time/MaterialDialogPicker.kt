package com.eneskayiklik.eventverse.feature.create.component.date_time

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.eneskayiklik.eventverse.feature.create.util.DatePickerDialogImpl
import com.eneskayiklik.eventverse.feature.create.util.PickerState
import com.eneskayiklik.eventverse.feature.create.util.PickerType
import com.eneskayiklik.eventverse.feature.create.util.rememberPickerState
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun MaterialDialogPicker(
    state: PickerState = rememberPickerState(),
    pickerType: PickerType = PickerType.DATE,
    onDate: (LocalDate) -> Unit = {},
    onTime: (LocalTime) -> Unit = {}
) {
    val context = LocalContext.current
    val picker = remember { DatePickerDialogImpl(context, state) }
    if (state.showing) {
        when (pickerType) {
            PickerType.DATE -> picker.showDatePicker(onDate)
            PickerType.TIME -> picker.showTimePicker(onTime)
        }
    }
}