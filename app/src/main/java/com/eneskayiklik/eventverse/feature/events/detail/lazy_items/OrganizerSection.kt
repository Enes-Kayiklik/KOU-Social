package com.eneskayiklik.eventverse.feature.events.detail.lazy_items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.data.model.auth.PostUser
import com.eneskayiklik.eventverse.feature.profile.profile.component.ProfileImage

fun LazyListScope.organizerSection(
    user: PostUser,
    onClick: () -> Unit
) {
    item {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClick
                )
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            ProfileImage(picUrl = user.profilePic, Modifier.size(32.dp))
            Text(
                text = stringResource(id = R.string.organized_by),
                style = MaterialTheme.typography.h4.copy(
                    color = MaterialTheme.colors.onSecondary,
                    fontSize = 12.sp
                )
            )
            Text(
                text = user.fullName, style = MaterialTheme.typography.h1.copy(
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 14.sp
                )
            )
        }
    }
}