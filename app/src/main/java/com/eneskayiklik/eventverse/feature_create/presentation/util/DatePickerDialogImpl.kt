package com.eneskayiklik.eventverse.feature_create.presentation.util

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import java.time.LocalDate
import java.time.LocalTime

class DatePickerDialogImpl(private val context: Context, private val state: PickerState) {
    fun showDatePicker(onDate: (LocalDate) -> Unit) {
        val current = LocalDate.now()
        val dialog = DatePickerDialog(context, { _, y, m, d ->
            onDate(LocalDate.of(y, m + 1, d))
            state.hide()
        }, current.year, current.month.value - 1, current.dayOfMonth)
        dialog.setOnDismissListener { state.hide() }
        dialog.datePicker.minDate = System.currentTimeMillis()
        dialog.show()
    }

    fun showTimePicker(onTime: (LocalTime) -> Unit) {
        val current = LocalTime.now()
        val dialog = TimePickerDialog(context, { _, h, m ->
            onTime(LocalTime.of(h, m))
            state.hide()
        }, current.hour, current.minute, true)
        dialog.setOnDismissListener { state.hide() }
        dialog.show()
    }
}

@Composable
fun rememberPickerState(): PickerState {
    return rememberSaveable(saver = PickerState.Saver()) {
        PickerState()
    }
}

class PickerState {
    var showing by mutableStateOf(false)

    fun show() {
        showing = true
    }

    fun hide() {
        showing = false
    }

    companion object {
        fun Saver(): Saver<PickerState, *> = Saver(
            save = { it.showing },
            restore = { PickerState() }
        )
    }
}