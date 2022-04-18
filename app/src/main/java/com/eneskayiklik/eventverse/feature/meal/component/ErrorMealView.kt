package com.eneskayiklik.eventverse.feature.meal.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eneskayiklik.eventverse.R

@Composable
fun ErrorMealView(
    modifier: Modifier = Modifier,
    title: String = "",
    subtitle: String = "",
) {
    val errorTitle = if (title.isEmpty()) stringResource(id = R.string.no_meal_title) else title
    val errorSubtitle = if (title.isEmpty()) stringResource(id = R.string.no_meal_desc) else title
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_pizza),
            contentDescription = null,
            modifier = Modifier
                .alpha(.3F)
                .size(100.dp), tint = MaterialTheme.colors.primary
        )
        Text(
            text = errorTitle,
            style = MaterialTheme.typography.h1.copy(
                color = MaterialTheme.colors.onBackground,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        )

        Text(
            text = errorSubtitle,
            style = MaterialTheme.typography.h1.copy(
                color = MaterialTheme.colors.onSurface,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        )
    }
}