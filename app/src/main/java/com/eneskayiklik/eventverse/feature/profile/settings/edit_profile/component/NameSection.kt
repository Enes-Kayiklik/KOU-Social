package com.eneskayiklik.eventverse.feature.profile.settings.edit_profile.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.core.component.ExtendedTextField
import com.eneskayiklik.eventverse.util.TextFieldState
import com.eneskayiklik.eventverse.feature.create.util.PickerState
import com.eneskayiklik.eventverse.feature.create.util.rememberPickerState
import com.eneskayiklik.eventverse.feature.create.component.date_time.AgeSelectionItem
import com.eneskayiklik.eventverse.feature.create.component.date_time.MaterialDialogPicker
import com.eneskayiklik.eventverse.feature.create.util.PickerType
import com.eneskayiklik.eventverse.data.event.profile.EditProfileEvent
import com.eneskayiklik.eventverse.data.model.auth.Department
import com.eneskayiklik.eventverse.feature.profile.settings.edit_profile.component.department.DepartmentPopup
import java.time.LocalDate

fun LazyListScope.nameSection(
    fullName: TextFieldState,
    event: (EditProfileEvent) -> Unit
) {
    item {
        SingleSection(
            titleText = stringResource(id = R.string.edit_name),
            fieldText = fullName.text,
            error = fullName.error,
            fieldPlaceholder = stringResource(id = R.string.fullname_paceholder),
            onValueChange = { event(EditProfileEvent.OnFullName(it)) }
        )
    }
}

fun LazyListScope.ageSection(
    age: String,
    event: (EditProfileEvent) -> Unit
) {
    item {
        val pickerState = rememberPickerState()
        DatePicker(
            pickerState = pickerState,
            onDateSelected = { event(EditProfileEvent.OnBirthdate(it)) })
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Text(
                text = stringResource(id = R.string.edit_age),
                style = MaterialTheme.typography.h1.copy(
                    MaterialTheme.colors.onSurface,
                    fontSize = 14.sp
                ), modifier = Modifier.weight(1F)
            )
            AgeSelectionItem(
                modifier = Modifier.weight(2F),
                hint = stringResource(id = R.string.birthdate),
                text = age
            ) {
                pickerState.show()
            }
        }
    }
}

fun LazyListScope.departmentSection(
    department: Department,
    isPopupVisible: Boolean,
    event: (EditProfileEvent) -> Unit
) {
    item {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Text(
                text = stringResource(id = R.string.select_department_label),
                style = MaterialTheme.typography.h1.copy(
                    MaterialTheme.colors.onSurface,
                    fontSize = 14.sp
                ), modifier = Modifier.weight(1F)
            )
            AgeSelectionItem(
                modifier = Modifier.weight(2F),
                hint = stringResource(id = R.string.select_department_placeholder),
                text = department.departmentName
            ) {
                event(EditProfileEvent.ShowDepartmentPopup(true))
            }
        }

        if (isPopupVisible) DepartmentPopup {
            if (it.departmentName.isNotEmpty()) event(EditProfileEvent.OnDepartment(it))
            else event(EditProfileEvent.ShowDepartmentPopup(false))
        }
    }
}

fun LazyListScope.socialSection(
    instagram: TextFieldState,
    twitter: TextFieldState,
    github: TextFieldState,
    linkedIn: TextFieldState,
    event: (EditProfileEvent) -> Unit
) {
    item {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            SingleSection(
                titleText = stringResource(id = R.string.instagram),
                fieldText = instagram.text,
                error = instagram.error,
                fieldPlaceholder = stringResource(id = R.string.instagram),
                onValueChange = { event(EditProfileEvent.OnInstagram(it)) })
            SingleSection(
                titleText = stringResource(id = R.string.github),
                fieldText = github.text,
                error = github.error,
                fieldPlaceholder = stringResource(id = R.string.github),
                onValueChange = { event(EditProfileEvent.OnGitHub(it)) })
            SingleSection(
                titleText = stringResource(id = R.string.twitter),
                fieldText = twitter.text,
                error = twitter.error,
                fieldPlaceholder = stringResource(id = R.string.twitter),
                onValueChange = { event(EditProfileEvent.OnTwitter(it)) })
            SingleSection(
                titleText = stringResource(id = R.string.linkedin),
                fieldText = linkedIn.text,
                error = linkedIn.error,
                fieldPlaceholder = stringResource(id = R.string.linkedin),
                onValueChange = { event(EditProfileEvent.OnLinkedIn(it)) })
        }
    }
}

@Composable
private fun SingleSection(
    titleText: String,
    fieldText: String,
    fieldPlaceholder: String,
    error: String,
    onValueChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
    ) {
        Text(
            text = titleText,
            style = MaterialTheme.typography.h1.copy(
                MaterialTheme.colors.onSurface,
                fontSize = 14.sp
            ), modifier = Modifier.weight(1F)
        )
        ExtendedTextField(
            text = fieldText,
            onValueChange = onValueChange,
            error = error,
            placeholder = fieldPlaceholder,
            keyboardType = KeyboardType.Email,
            modifier = Modifier.weight(2F)
        )
    }
}

@Composable
private fun DatePicker(
    pickerState: PickerState = rememberPickerState(),
    dialogType: PickerType = PickerType.DATE,
    onDateSelected: (LocalDate) -> Unit
) {
    MaterialDialogPicker(
        state = pickerState,
        dialogType, onDate = onDateSelected, onTime = { }
    )
}