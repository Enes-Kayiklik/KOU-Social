package com.eneskayiklik.eventverse.feature_settings.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.core.ui.theme.Blue
import com.eneskayiklik.eventverse.core.ui.theme.Orange
import com.eneskayiklik.eventverse.core.ui.theme.Purple
import com.eneskayiklik.eventverse.core.ui.theme.Red
import com.eneskayiklik.eventverse.feature_auth.data.model.User
import com.eneskayiklik.eventverse.feature_profile.presentation.component.ProfileImage

fun LazyListScope.editProfileButton(
    user: User,
    onClick: () -> Unit
) {
    item {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(horizontal = 32.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileImage(picUrl = user.profilePic, modifier = Modifier.size(50.dp))
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
                    text = stringResource(id = R.string.settings_edit_profile),
                    style = MaterialTheme.typography.h1.copy(
                        MaterialTheme.colors.onSurface,
                        fontSize = 12.sp
                    )
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_right_arrow),
                contentDescription = null,
                modifier = Modifier
                    .background(
                        MaterialTheme.colors.secondary,
                        RoundedCornerShape(15.dp)
                    )
                    .padding(8.dp)
            )
        }
    }
}

fun LazyListScope.languageButton(
    onClick: () -> Unit
) {
    item {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(horizontal = 32.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_globe),
                contentDescription = null,
                modifier = Modifier
                    .background(
                        Orange.copy(alpha = 0.2F),
                        CircleShape
                    )
                    .padding(8.dp),
                tint = Orange
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(8F)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.language),
                    style = MaterialTheme.typography.h1.copy(
                        MaterialTheme.colors.onBackground,
                        fontSize = 16.sp
                    )
                )
                Text(
                    text = stringResource(id = R.string.settings_edit_profile),
                    style = MaterialTheme.typography.h1.copy(
                        MaterialTheme.colors.onSurface,
                        fontSize = 12.sp
                    )
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_right_arrow),
                contentDescription = null,
                modifier = Modifier
                    .background(
                        MaterialTheme.colors.secondary,
                        RoundedCornerShape(15.dp)
                    )
                    .padding(8.dp)
            )
        }
    }
}

fun LazyListScope.darkModeButton(
    onClick: () -> Unit
) {
    item {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(horizontal = 32.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_moon),
                contentDescription = null,
                modifier = Modifier
                    .background(
                        Purple.copy(alpha = 0.2F),
                        CircleShape
                    )
                    .padding(8.dp),
                tint = Purple
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(8F)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.dark_mode),
                    style = MaterialTheme.typography.h1.copy(
                        MaterialTheme.colors.onBackground,
                        fontSize = 16.sp
                    )
                )
                Text(
                    text = stringResource(id = R.string.settings_edit_profile),
                    style = MaterialTheme.typography.h1.copy(
                        MaterialTheme.colors.onSurface,
                        fontSize = 12.sp
                    )
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_right_arrow),
                contentDescription = null,
                modifier = Modifier
                    .background(
                        MaterialTheme.colors.secondary,
                        RoundedCornerShape(15.dp)
                    )
                    .padding(8.dp)
            )
        }
    }
}

fun LazyListScope.inviteFriendButton(
    onClick: () -> Unit
) {
    item {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(horizontal = 32.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_send),
                contentDescription = null,
                modifier = Modifier
                    .background(
                        Red.copy(alpha = 0.2F),
                        CircleShape
                    )
                    .padding(8.dp),
                tint = Red
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(8F)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.invite_friend),
                    style = MaterialTheme.typography.h1.copy(
                        MaterialTheme.colors.onBackground,
                        fontSize = 16.sp
                    )
                )
                Text(
                    text = stringResource(id = R.string.invite_friend_desc),
                    style = MaterialTheme.typography.h1.copy(
                        MaterialTheme.colors.onSurface,
                        fontSize = 12.sp
                    )
                )
            }
        }
    }
}

fun LazyListScope.verifyAccountButton(
    onClick: () -> Unit
) {
    item {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(horizontal = 32.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_verify),
                contentDescription = null,
                modifier = Modifier
                    .background(
                        Blue.copy(alpha = 0.2F),
                        CircleShape
                    )
                    .padding(8.dp),
                tint = Blue
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(8F)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.verify_account),
                    style = MaterialTheme.typography.h1.copy(
                        MaterialTheme.colors.onBackground,
                        fontSize = 16.sp
                    )
                )
                Text(
                    text = stringResource(id = R.string.verify_account_desc),
                    style = MaterialTheme.typography.h1.copy(
                        MaterialTheme.colors.onSurface,
                        fontSize = 12.sp
                    )
                )
            }
        }
    }
}