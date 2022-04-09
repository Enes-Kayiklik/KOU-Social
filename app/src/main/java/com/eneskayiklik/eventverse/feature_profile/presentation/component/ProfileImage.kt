package com.eneskayiklik.eventverse.feature_profile.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.eneskayiklik.eventverse.R

@Composable
fun ProfileImage(
    picUrl: String,
    modifier: Modifier = Modifier
) {
    if (picUrl.isNotEmpty())
        Image(
            painter = rememberImagePainter(data = picUrl) {
                crossfade(300)
            },
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .clip(CircleShape)
                .shadow(5.dp, CircleShape)
        ) else Box(
        modifier = modifier.background(
            MaterialTheme.colors.secondary,
            CircleShape
        ), contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_user),
            contentDescription = null,
            tint = MaterialTheme.colors.onSecondary,
            modifier = Modifier.fillMaxSize(.5F)
        )
    }
}