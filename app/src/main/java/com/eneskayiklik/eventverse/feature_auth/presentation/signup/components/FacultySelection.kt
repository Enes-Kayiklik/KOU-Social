package com.eneskayiklik.eventverse.feature_auth.presentation.signup.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.core.component.ExtendedTextField
import com.eneskayiklik.eventverse.feature_auth.data.event.SignupEvent
import com.eneskayiklik.eventverse.feature_auth.domain.model.Department
import com.eneskayiklik.eventverse.feature_auth.domain.model.Faculty

@ExperimentalAnimationApi
@Composable
fun FacultySelection(
    selectedDepartment: Department,
    facultyList: List<Faculty>,
    isPopupActive: Boolean,
    onEvent: (SignupEvent) -> Unit
) {
    ExtendedTextField(
        text = if (selectedDepartment.departmentName.isNotEmpty()) selectedDepartment.departmentName else "",
        onValueChange = { },
        error = "",
        enabled = false,
        placeholder = stringResource(id = R.string.select_department_placeholder),
        label = stringResource(id = R.string.select_department_label),
        keyboardType = KeyboardType.NumberPassword,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onEvent(SignupEvent.ShowFacultyPopup) },
    )

    if (isPopupActive) FacultyPopup(facultyList) { onEvent(SignupEvent.SelectDepartment(it)) }
}