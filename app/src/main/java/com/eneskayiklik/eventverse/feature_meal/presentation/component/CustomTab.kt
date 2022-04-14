package com.eneskayiklik.eventverse.feature_meal.presentation.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTab(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String
) {
    val titleColor =
        animateColorAsState(targetValue = if (selected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface).value
    val subTitleColor =
        animateColorAsState(targetValue = if (selected) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface).value
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .height(70.dp)
            .padding(bottom = 1.dp, top = 10.dp)
            .width(30.dp)
            .clickable(
                onClick = onClick,
                interactionSource = MutableInteractionSource(),
                indication = null
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title, style = MaterialTheme.typography.h2.copy(
                color = titleColor,
                fontSize = 14.sp
            )
        )
        Box(
            modifier = Modifier
                .size(37.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colors.background), contentAlignment = Alignment.Center
        ) {

            Text(
                text = subtitle, style = MaterialTheme.typography.h1.copy(
                    color = subTitleColor,
                    fontSize = 18.sp
                )
            )
        }
    }
}