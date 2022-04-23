package com.eneskayiklik.eventverse.feature.share.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.eneskayiklik.eventverse.R

@Composable
fun ShareContentField(
    text: String,
    onValueChange: (String) -> Unit
) {
    Box(contentAlignment = Alignment.CenterStart) {
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.h1.copy(
                color = MaterialTheme.colors.onBackground,
                fontSize = 16.sp
            ), cursorBrush = SolidColor(MaterialTheme.colors.primary)
        )
        if (text.isEmpty())
            Text(
                text = stringResource(id = R.string.share_write_desc),
                style = MaterialTheme.typography.h1.copy(
                    color = MaterialTheme.colors.onSecondary,
                    fontSize = 16.sp
                )
            )
    }
}