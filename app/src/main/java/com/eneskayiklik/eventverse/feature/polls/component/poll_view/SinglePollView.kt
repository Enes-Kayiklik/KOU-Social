package com.eneskayiklik.eventverse.feature.polls.component.poll_view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eneskayiklik.eventverse.data.model.auth.PostUser
import com.eneskayiklik.eventverse.data.model.poll.Poll
import com.eneskayiklik.eventverse.feature.profile.profile.component.ProfileImage

@Composable
fun SinglePollView(
    poll: Poll,
    modifier: Modifier = Modifier,
    onOptionSelected: (pollId: String, index: Int) -> Unit
) {
    Column(
        modifier = modifier
            .padding(24.dp), verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        UserSection(
            poll.fromUser,
            poll.formattedDate
        ) {

        }
        Text(
            text = poll.title, style = MaterialTheme.typography.h1.copy(
                color = MaterialTheme.colors.onBackground,
                fontSize = 20.sp
            ), modifier = Modifier.padding(vertical = 10.dp)
        )
        PollOptionsList(
            poll.userAnswer, poll.showAnswers, poll.percentages, poll.options
        ) {
            // if user rated once then can't re rate
            if (poll.showAnswers.not()) onOptionSelected(poll.id, it)
        }
    }
}

@Composable
fun UserSection(
    user: PostUser,
    date: String,
    modifier: Modifier = Modifier,
    onUserClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProfileImage(
            picUrl = user.profilePic,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .clickable { onUserClick() })
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(8F)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = user.fullName,
                style = MaterialTheme.typography.h1.copy(
                    MaterialTheme.colors.onBackground,
                    fontSize = 16.sp
                )
            )
            Text(
                text = user.department,
                style = MaterialTheme.typography.h1.copy(
                    MaterialTheme.colors.onSurface,
                    fontSize = 12.sp
                )
            )
        }
        Text(
            text = date,
            style = MaterialTheme.typography.h1.copy(
                MaterialTheme.colors.onSurface,
                fontSize = 12.sp
            )
        )
    }
}