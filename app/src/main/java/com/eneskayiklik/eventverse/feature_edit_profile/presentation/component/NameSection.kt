package com.eneskayiklik.eventverse.feature_edit_profile.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.core.component.ExtendedTextField

fun LazyListScope.nameSection(
    fullName: String,
    onChange: (String) -> Unit
) {
    item {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Text(
                text = stringResource(id = R.string.edit_name),
                style = MaterialTheme.typography.h1.copy(
                    MaterialTheme.colors.onSurface,
                    fontSize = 14.sp
                ), modifier = Modifier.weight(1F)
            )
            ExtendedTextField(
                text = fullName,
                onValueChange = onChange,
                error = "",
                placeholder = stringResource(id = R.string.email_paceholder),
                keyboardType = KeyboardType.Email,
                modifier = Modifier.weight(2F)
            )
        }
    }
}

fun LazyListScope.ageSection(
    age: String,
    onChange: (String) -> Unit
) {
    item {
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
            ExtendedTextField(
                text = age,
                onValueChange = onChange,
                error = "",
                placeholder = stringResource(id = R.string.email_paceholder),
                keyboardType = KeyboardType.Email,
                modifier = Modifier.weight(2F)
            )
        }
    }
}