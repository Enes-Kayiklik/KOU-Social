package com.eneskayiklik.eventverse.feature_meal.presentation.component.meal_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eneskayiklik.eventverse.R

@Composable
fun DateItem(
    text: String,
    onClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.h1.copy(
                fontSize = 32.sp,
                color = MaterialTheme.colors.onBackground
            )
        )
        ShareButton(onClick)
    }
}

@Composable
fun ShareButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(30.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colors.primary)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_share),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(.5F), tint = MaterialTheme.colors.onPrimary
        )
    }
}

@Composable
fun MealSectionTitle(
    text: String
) {
    Text(
        text = text,
        style = MaterialTheme.typography.h1.copy(
            fontSize = 20.sp,
            color = MaterialTheme.colors.onSecondary
        )
    )
}