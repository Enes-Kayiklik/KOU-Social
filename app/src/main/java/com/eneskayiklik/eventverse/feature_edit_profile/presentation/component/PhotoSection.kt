package com.eneskayiklik.eventverse.feature_edit_profile.presentation.component

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import com.eneskayiklik.eventverse.feature_edit_profile.data.event.EditProfileEvent
import com.eneskayiklik.eventverse.feature_profile.presentation.component.ProfileImage

fun LazyListScope.photoSection(
    profilePic: String,
    event: (EditProfileEvent) -> Unit
) {
    item {
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) {
            if (it != null) event(EditProfileEvent.OnPhoto(it))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Text(
                text = stringResource(id = R.string.edit_photo),
                style = MaterialTheme.typography.h1.copy(
                    MaterialTheme.colors.onSurface,
                    fontSize = 14.sp
                ), modifier = Modifier.weight(1F)
            )
            Box(
                modifier = Modifier
                    .weight(2F)
                    .clickable(
                        remember { MutableInteractionSource() },
                        null,
                        onClick = { launcher.launch("image/*") })
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ProfileImage(picUrl = profilePic, modifier = Modifier.size(72.dp))
                    Text(
                        text = stringResource(id = R.string.update_photo),
                        style = MaterialTheme.typography.h1.copy(
                            MaterialTheme.colors.onSurface,
                            fontSize = 18.sp
                        )
                    )
                }
            }
        }
    }
}