package com.eneskayiklik.eventverse.feature.events.detail.lazy_items

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.data.model.auth.PostUser
import com.eneskayiklik.eventverse.data.model.create.Event
import com.eneskayiklik.eventverse.feature.profile.profile.component.ProfileImage

fun LazyListScope.detailThings(
    event: Event
) {
    item {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(start = 16.dp)
        ) {
            SingleDetailItem(icon = R.drawable.ic_calendar, title = event.fullDate)
            Spacer(modifier = Modifier.height(0.dp))
            SingleDetailItem(icon = R.drawable.ic_location, title = event.location)
            if (event.showAttendeeCount) {
                Spacer(modifier = Modifier.height(0.dp))
                SingleDetailItem(
                    icon = R.drawable.ic_people,
                    title = stringResource(id = R.string.going_count, event.totalAttendeeCount)
                )
            }
            if (event.showUserHeads) UserHeadList(users = event.attendee)
        }
    }
}

@Composable
private fun SingleDetailItem(
    @DrawableRes icon: Int,
    title: String
) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = MaterialTheme.colors.onSecondary
        )
        Text(
            text = title, style = MaterialTheme.typography.h4.copy(
                color = MaterialTheme.colors.onBackground,
                fontSize = 14.sp
            ), modifier = Modifier.padding(end = 16.dp)
        )
    }
}

@Composable
private fun UserHeadList(
    users: List<PostUser>
) {
    Row(
        modifier = Modifier.padding(start = 30.dp),
        horizontalArrangement = Arrangement.spacedBy(-(12.dp))
    ) {
        users.forEach {
            ProfileImage(
                picUrl = it.profilePic,
                modifier = Modifier
                    .size(32.dp)
                    .border(1.5.dp, MaterialTheme.colors.background, CircleShape)
            )
        }
    }
}