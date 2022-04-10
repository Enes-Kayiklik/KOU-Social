package com.eneskayiklik.eventverse.feature_edit_profile.presentation.component

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
import com.eneskayiklik.eventverse.feature_auth.domain.model.Department
import com.eneskayiklik.eventverse.feature_auth.domain.model.SocialAccount
import com.eneskayiklik.eventverse.feature_create.presentation.component.date_time.AgeSelectionItem
import com.eneskayiklik.eventverse.feature_create.presentation.component.date_time.MaterialDialogPicker
import com.eneskayiklik.eventverse.feature_create.presentation.util.*
import java.time.LocalDate

fun LazyListScope.nameSection(
    fullName: String,
    onChange: (String) -> Unit
) {
    item {
        SingleSection(
            titleText = stringResource(id = R.string.edit_name),
            fieldText = fullName,
            fieldPlaceholder = stringResource(id = R.string.fullname_paceholder),
            onValueChange = onChange
        )
    }
}

fun LazyListScope.ageSection(
    age: String,
    onDateSelected: (LocalDate) -> Unit
) {
    item {
        val pickerState = rememberPickerState()
        DatePicker(pickerState = pickerState, onDateSelected = onDateSelected)
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
    onDepartmentSelected: (String) -> Unit
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
                // TODO("Open department popup")
            }
        }
    }
}

fun LazyListScope.socialSection(
    socialAccount: SocialAccount
) {
    item {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            SingleSection(
                titleText = stringResource(id = R.string.instagram),
                fieldText =socialAccount.instagram,
                fieldPlaceholder = stringResource(id = R.string.instagram),
                onValueChange = { })
            SingleSection(
                titleText = stringResource(id = R.string.github),
                fieldText = socialAccount.github,
                fieldPlaceholder = stringResource(id = R.string.github),
                onValueChange = { })
            SingleSection(
                titleText = stringResource(id = R.string.twitter),
                fieldText = socialAccount.twitter,
                fieldPlaceholder = stringResource(id = R.string.twitter),
                onValueChange = { })
            SingleSection(
                titleText = stringResource(id = R.string.linkedin),
                fieldText = socialAccount.linkedIn,
                fieldPlaceholder = stringResource(id = R.string.linkedin),
                onValueChange = { })
        }
    }
}

@Composable
private fun SingleSection(
    titleText: String,
    fieldText: String,
    fieldPlaceholder: String,
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
            error = "",
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