package com.eneskayiklik.eventverse.feature.profile.profile.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.util.Screen

@Composable
fun ActionButtons(
    modifier: Modifier = Modifier,
    userId: String,
    onNavigate: (String) -> Unit
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(2.dp)) {
        SingleButton(
            icon = R.drawable.ic_poll,
            title = stringResource(id = R.string.profile_polls_title),
            subtitle = stringResource(id = R.string.profile_polls_desc),
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                .clickable {
                    onNavigate(Screen.ProfilePolls.route(userId))
                }
        )
        SingleButton(
            icon = R.drawable.ic_pen,
            title = stringResource(id = R.string.profile_posts_title),
            subtitle = stringResource(id = R.string.profile_posts_desc),
            modifier = Modifier
                .clickable {
                    onNavigate(Screen.ProfilePosts.route(userId))
                }
        )
        SingleButton(
            icon = R.drawable.ic_like,
            title = stringResource(id = R.string.profile_liked_events_title),
            subtitle = stringResource(id = R.string.profile_liked_events_desc),
            modifier = Modifier
                .clickable {
                    onNavigate(Screen.ProfilePosts.route(userId))
                }
        )
        SingleButton(
            icon = R.drawable.ic_attendee,
            title = stringResource(id = R.string.profile_attendee_title),
            subtitle = stringResource(id = R.string.profile_attendee_desc),
            modifier = Modifier
                .clip(RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp))
                .clickable {
                    onNavigate(Screen.ProfilePosts.route(userId))
                }
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