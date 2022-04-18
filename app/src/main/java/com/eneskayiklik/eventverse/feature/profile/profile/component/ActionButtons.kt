package com.eneskayiklik.eventverse.feature.profile.profile.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eneskayiklik.eventverse.R

@Composable
fun ActionButtons(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(2.dp)) {
        SingleButton(
            icon = R.drawable.ic_poll,
            title = "Polls",
            subtitle = "Lorem ipsum dolar sit amet",
            modifier = Modifier.clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
        )
        SingleButton(
            icon = R.drawable.ic_pen,
            title = "Posts",
            subtitle = "Lorem ipsum dolar sit amet",
        )
        SingleButton(
            icon = R.drawable.ic_reviews,
            title = "Reviews",
            subtitle = "Lorem ipsum dolar sit amet",
        )
        SingleButton(
            icon = R.drawable.ic_attendee,
            title = "Attendee",
            subtitle = "Lorem ipsum dolar sit amet",
            modifier = Modifier.clip(RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp))

        )
    }
}

@Composable
private fun SingleButton(
    @DrawableRes icon: Int,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colors.secondary)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier
                .weight(1F)
                .fillMaxHeight()
        ) {
            Icon(painter = painterResource(id = icon), contentDescription = null)
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(8F)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h1.copy(
                    MaterialTheme.colors.onBackground,
                    fontSize = 16.sp
                )
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.h1.copy(
                    MaterialTheme.colors.onSurface,
                    fontSize = 12.sp
                )
            )
        }
        Box(
            modifier = Modifier
                .weight(1F)
                .fillMaxHeight()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_right_arrow),
                contentDescription = null,
                tint = MaterialTheme.colors.onBackground
            )
        }
    }
}