package com.eneskayiklik.eventverse.core.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import com.eneskayiklik.eventverse.core.ui.theme.UnselectedColor

@Composable
fun RowScope.BaseBottomNavItem(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    contentDescription: String? = null,
    selected: Boolean = false,
    selectedColor: Color = MaterialTheme.colors.primary,
    unselectedColor: Color = UnselectedColor,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    BottomNavigationItem(
        selected = selected,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        selectedContentColor = selectedColor,
        unselectedContentColor = unselectedColor,
        icon = {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = contentDescription,
                    modifier = Modifier
                )
            }
        },
        label = {
            if (!contentDescription.isNullOrEmpty()) {
                Text(
                    text = contentDescription,
                    style = MaterialTheme.typography.h2.copy(fontSize = 11.sp)
                )
            }
        }
    )
}