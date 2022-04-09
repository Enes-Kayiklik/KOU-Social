package com.eneskayiklik.eventverse.feature_explore.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.core.util.Settings
import com.eneskayiklik.eventverse.feature_profile.presentation.component.ProfileImage

@Composable
fun ExploreToolbar(
    modifier: Modifier = Modifier,
    onStartIconClick: () -> Unit = { },
    onEndIconClick: () -> Unit = { },
) {
    Box(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.app_name).lowercase(),
            style = MaterialTheme.typography.h3.copy(
                color = MaterialTheme.colors.onBackground,
                fontSize = 28.sp
            ),
            modifier = Modifier.align(Alignment.Center)
        )

        IconButton(onClick = onEndIconClick, modifier = Modifier.align(Alignment.CenterEnd)) {
            ProfileImage(picUrl = Settings.currentUser.profilePic, modifier = Modifier.size(32.dp))
        }
    }
}