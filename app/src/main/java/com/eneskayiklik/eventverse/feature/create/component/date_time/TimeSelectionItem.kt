package com.eneskayiklik.eventverse.feature.create.component.date_time

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eneskayiklik.eventverse.core.component.ExtendedTextField
import com.eneskayiklik.eventverse.feature.create.component.SectionTitle

@ExperimentalMaterialApi
@Composable
fun TimeSelectionItem(
    modifier: Modifier = Modifier,
    headerText: String,
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Column(modifier = modifier) {
        SectionTitle(title = headerText)
        Spacer(modifier = Modifier.height(5.dp))
        Surface(
            onClick = onClick,
            elevation = 2.dp,
            shape = RoundedCornerShape(5.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = text, style = MaterialTheme.typography.h1.copy(fontSize = 16.sp))
                Icon(
                    imageVector = icon,
                    contentDescription = headerText,
                    tint = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@Composable
fun AgeSelectionItem(
    modifier: Modifier = Modifier,
    text: String,
    hint: String,
    onClick: () -> Unit
) {
    ExtendedTextField(
        text = text,
        placeholder = hint,
        enabled = false,
        modifier = modifier.clickable(onClick = onClick),
        onValueChange = { })
}