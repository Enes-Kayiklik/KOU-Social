package com.eneskayiklik.eventverse.core.component

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BaseBottomNavItem(
    @DrawableRes icon: Int? = null,
    contentDescription: String? = null,
    selected: Boolean = false,
    selectedColor: Color = MaterialTheme.colors.primary,
    onClick: () -> Unit
) {
    val backColor by animateColorAsState(targetValue = if (selected) selectedColor.copy(alpha = .2F) else Color.Transparent)
    val itemsColor by animateColorAsState(targetValue = if (selected) selectedColor else MaterialTheme.colors.onSecondary)
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(backColor)
            .animateContentSize()
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp)
        ) {
            if (icon != null) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = contentDescription,
                    modifier = Modifier,
                    tint = itemsColor,
                )
            }
            if (!contentDescription.isNullOrEmpty() && selected) {
                Text(
                    text = contentDescription,
                    style = MaterialTheme.typography.h2.copy(fontSize = 14.sp, color = itemsColor)
                )
            }
        }
    }
}