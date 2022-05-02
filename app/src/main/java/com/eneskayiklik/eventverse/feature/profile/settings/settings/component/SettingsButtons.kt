package com.eneskayiklik.eventverse.feature.profile.settings.settings.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.core.ui.theme.*
import com.eneskayiklik.eventverse.core.ui.theme.Blue
import com.eneskayiklik.eventverse.data.model.auth.User
import com.eneskayiklik.eventverse.data.model.profile.DarkMode
import com.eneskayiklik.eventverse.data.model.profile.Language
import com.eneskayiklik.eventverse.data.model.profile.selectableLanguages
import com.eneskayiklik.eventverse.data.model.profile.selectableModes
import com.eneskayiklik.eventverse.feature.profile.profile.component.ProfileImage

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
            ProfileImage(picUrl = user.profilePic, modifier = Modifier.size(72.dp))
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
                    text = user.email,
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
    activeLanguage: Language,
    onLanguageSelected: (Int) -> Unit
) {
    item {
        var expanded by remember { mutableStateOf(false) }
        var dropdownOffset by remember { mutableStateOf(DpOffset(0.dp, 0.dp)) }

        Box {
            SettingsButton(
                onClick = { expanded = expanded.not() },
                title = stringResource(id = R.string.language),
                subtitle = activeLanguage.value,
                icon = R.drawable.ic_globe,
                color = Orange,
                isEndButtonActive = true,
                isEndButtonDropdown = true,
                modifier = Modifier.onGloballyPositioned {
                    val w = it.boundsInParent().topRight.x
                    dropdownOffset = DpOffset(w.dp - 24.dp, (-60).dp)
                }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = expanded.not() },
                offset = dropdownOffset
            ) {
                selectableLanguages.forEachIndexed { i, item ->
                    DropdownMenuItem(onClick = {
                        expanded = expanded.not()
                        onLanguageSelected(i)
                    }) {
                        Text(
                            text = item.value, style = MaterialTheme.typography.h1.copy(
                                fontSize = 16.sp,
                                color = MaterialTheme.colors.onBackground
                            )
                        )
                    }
                    if (i != selectableLanguages.lastIndex)
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp)
                                .height(1.dp)
                                .background(MaterialTheme.colors.onSecondary.copy(alpha = .3F))
                        )
                }
            }
        }
    }
}

fun LazyListScope.darkModeButton(
    activeMode: DarkMode,
    onModeSelected: (Int) -> Unit
) {
    item {
        var expanded by remember { mutableStateOf(false) }
        var dropdownOffset by remember { mutableStateOf(DpOffset(0.dp, 0.dp)) }

        Box {
            SettingsButton(
                onClick = { expanded = expanded.not() },
                title = stringResource(id = R.string.dark_mode),
                subtitle = stringResource(id = activeMode.title),
                icon = R.drawable.ic_moon,
                color = Purple,
                isEndButtonActive = true,
                isEndButtonDropdown = true,
                modifier = Modifier.onGloballyPositioned {
                    val w = it.boundsInParent().topRight.x
                    dropdownOffset = DpOffset(w.dp - 24.dp, (-60).dp)
                }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = expanded.not() },
                offset = dropdownOffset
            ) {
                selectableModes.forEachIndexed { i, item ->
                    DropdownMenuItem(onClick = {
                        expanded = expanded.not()
                        onModeSelected(i)
                    }) {
                        Text(
                            text = stringResource(id = item.title),
                            style = MaterialTheme.typography.h1.copy(
                                fontSize = 16.sp,
                                color = MaterialTheme.colors.onBackground
                            )
                        )
                    }
                    if (i != selectableModes.lastIndex)
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp)
                                .height(1.dp)
                                .background(MaterialTheme.colors.onSecondary.copy(alpha = .3F))
                        )
                }
            }
        }
    }
}

fun LazyListScope.inviteFriendButton(
    onClick: () -> Unit
) {
    item {
        SettingsButton(
            onClick = onClick,
            title = stringResource(id = R.string.invite_friend),
            subtitle = stringResource(id = R.string.invite_friend_desc),
            icon = R.drawable.ic_message,
            color = GreenYellow,
            isEndButtonActive = false
        )
    }
}

fun LazyListScope.verifyAccountButton(
    isVerified: Boolean,
    onClick: () -> Unit
) {
    item {
        SettingsButton(
            onClick = onClick,
            title = stringResource(id = R.string.verify_account),
            subtitle = stringResource(id = if (isVerified.not()) R.string.verify_account_desc else R.string.account_verified),
            icon = R.drawable.ic_verify,
            color = Blue,
            isEndButtonActive = true
        )
    }
}

fun LazyListScope.updatePasswordButton(
    onClick: () -> Unit
) {
    item {
        SettingsButton(
            onClick = onClick,
            title = stringResource(id = R.string.change_password),
            subtitle = stringResource(id = R.string.change_password_desc),
            icon = R.drawable.ic_password,
            color = Turquoise,
            isEndButtonActive = true
        )
    }
}

fun LazyListScope.logoutButton(
    onClick: () -> Unit
) {
    item {
        SettingsButton(
            onClick = onClick,
            title = stringResource(id = R.string.logout),
            subtitle = stringResource(id = R.string.logout_desc),
            icon = R.drawable.ic_logout_filled,
            color = OrangeVariant,
            isEndButtonActive = false
        )
    }
}

fun LazyListScope.deleteAccountButton(
    isSocialLogin: Boolean,
    onClick: () -> Unit
) {
    item {
        SettingsButton(
            onClick = onClick,
            title = stringResource(id = R.string.delete_account),
            subtitle = stringResource(id = R.string.delete_account_desc),
            icon = R.drawable.ic_delete,
            color = Red,
            isEndButtonActive = isSocialLogin.not()
        )
    }
}

@Composable
private fun SettingsButton(
    onClick: () -> Unit,
    title: String,
    subtitle: String,
    @DrawableRes icon: Int,
    color: Color,
    modifier: Modifier = Modifier,
    isEndButtonActive: Boolean = false,
    isEndButtonDropdown: Boolean = false
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 32.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color.copy(alpha = 0.2F),
                    CircleShape
                ), contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(.5F),
                tint = color
            )
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(8F)
                .padding(horizontal = 16.dp)
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
        if (isEndButtonActive) Icon(
            painter = painterResource(id = R.drawable.ic_right_arrow),
            contentDescription = null,
            modifier = Modifier
                .background(
                    MaterialTheme.colors.secondary,
                    RoundedCornerShape(15.dp)
                )
                .padding(8.dp)
                .rotate(if (isEndButtonDropdown) 90F else 0F)
        )
    }
}