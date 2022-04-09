package com.eneskayiklik.eventverse.core.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eneskayiklik.eventverse.core.ui.theme.HintColor


@Composable
fun ExtendedTextField(
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester = FocusRequester(),
    text: String = "",
    label: String = "",
    placeholder: String = "",
    error: String = "",
    enabled: Boolean = true,
    style: TextStyle = MaterialTheme.typography.h4.copy(fontSize = 14.sp),
    singleLine: Boolean = true,
    maxLines: Int = 1,
    leadingIcon: ImageVector? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPasswordToggleDisplayed: Boolean = keyboardType == KeyboardType.Password,
    isPasswordVisible: Boolean = false,
    onPasswordToggleClick: (Boolean) -> Unit = {},
    onValueChangeLogic: (String) -> Boolean = { true },
    onValueChange: (String) -> Unit
) {
    val passwordVisibilityScale by animateFloatAsState(
        if (isPasswordToggleDisplayed && text.isNotEmpty()) 1F else 0F
    )

    val textColor by animateColorAsState(targetValue = if (error.isNotEmpty()) MaterialTheme.colors.error else MaterialTheme.colors.onBackground)

    Column(
        modifier = modifier
    ) {
        if (label.isNotEmpty()) {
            Text(
                text = label,
                style = MaterialTheme.typography.h2.copy(
                    fontSize = 17.sp,
                    color = MaterialTheme.colors.onBackground,
                ),
                modifier = Modifier.padding(bottom = 5.dp)
            )
        }
        OutlinedTextField(
            value = text,
            enabled = enabled,
            onValueChange = {
                if (onValueChangeLogic(it)) onValueChange(it)
            },
            maxLines = maxLines,
            textStyle = style.copy(color = textColor),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = MaterialTheme.colors.surface,
                focusedBorderColor = MaterialTheme.colors.primary,
                unfocusedBorderColor = Color.Transparent,
                errorBorderColor = MaterialTheme.colors.error,
                errorLabelColor = MaterialTheme.colors.error,
                errorTrailingIconColor = MaterialTheme.colors.error,
                disabledBorderColor = Color.Transparent
            ),
            label = null,
            placeholder = if (placeholder.isNotEmpty()) {
                {
                    Text(
                        text = placeholder,
                        style = MaterialTheme.typography.caption.copy(
                            fontSize = 14.sp,
                            color = HintColor
                        )
                    )
                }
            } else null,
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
                {
                    IconButton(
                        modifier = Modifier.scale(passwordVisibilityScale),
                        onClick = {
                            onPasswordToggleClick(isPasswordVisible.not())
                        }
                    ) {
                        Icon(
                            imageVector = if (isPasswordVisible) {
                                Icons.Rounded.VisibilityOff
                            } else {
                                Icons.Rounded.Visibility
                            },
                            tint = if (error.isNotEmpty()) MaterialTheme.colors.error else MaterialTheme.colors.primary,
                            contentDescription = null
                        )
                    }
                }
            } else null,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
        )
        AnimatedVisibility(visible = error.isNotEmpty()) {
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = error,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Start
            )
        }
    }
}