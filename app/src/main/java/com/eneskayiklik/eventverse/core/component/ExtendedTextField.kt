package com.eneskayiklik.eventverse.core.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.eneskayiklik.eventverse.R


@ExperimentalAnimationApi
@Composable
fun ExtendedTextField(
    modifier: Modifier = Modifier,
    text: String = "",
    label: String = "",
    error: String = "",
    style: TextStyle = TextStyle(
        color = MaterialTheme.colors.onBackground
    ),
    backgroundColor: Color = MaterialTheme.colors.surface,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    leadingIcon: ImageVector? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPasswordToggleDisplayed: Boolean = keyboardType == KeyboardType.Password,
    isPasswordVisible: Boolean = false,
    onPasswordToggleClick: (Boolean) -> Unit = {},
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = {
                onValueChange(it)
            },
            maxLines = maxLines,
            textStyle = style,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = backgroundColor
            ),
            label = {
                Text(
                    text = label,
                    style = MaterialTheme.typography.body1
                )
            },
            isError = error.isNotEmpty(),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),
            visualTransformation = if (!isPasswordVisible && isPasswordToggleDisplayed) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            singleLine = singleLine,
            leadingIcon = if (leadingIcon != null) {
                val icon: @Composable () -> Unit = {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onSurface
                    )
                }
                icon
            } else null,
            trailingIcon = if (isPasswordToggleDisplayed) {
                val icon: @Composable () -> Unit = {
                    IconButton(
                        onClick = {
                            onPasswordToggleClick(!isPasswordVisible)
                        }
                    ) {
                        Icon(
                            imageVector = if (isPasswordVisible) {
                                Icons.Filled.VisibilityOff
                            } else {
                                Icons.Filled.Visibility
                            },
                            tint = MaterialTheme.colors.onSurface,
                            contentDescription = stringResource(id = R.string.password)
                        )
                    }
                }
                icon
            } else null,
            modifier = Modifier
                .fillMaxWidth()
        )
        AnimatedVisibility(visible = error.isNotEmpty()) {
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = error,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}