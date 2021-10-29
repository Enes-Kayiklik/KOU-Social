package com.eneskayiklik.eventverse.feature_create.presentation.component.date_time

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.feature_create.presentation.util.PickerType
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun MaterialDialogPicker(
    dialogState: MaterialDialogState,
    pickerType: PickerType = PickerType.DATE,
    onDate: (LocalDate) -> Unit = {},
    onTime: (LocalTime) -> Unit = {}
) {
    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton(stringResource(id = R.string.ok))
            negativeButton(stringResource(id = R.string.cancel))
        }
    ) {
        when (pickerType) {
            PickerType.DATE -> {
                datepicker(onDateChange = onDate)
            }
            PickerType.TIME -> {
                timepicker(onTimeChange = onTime)
            }
        }
    }
}