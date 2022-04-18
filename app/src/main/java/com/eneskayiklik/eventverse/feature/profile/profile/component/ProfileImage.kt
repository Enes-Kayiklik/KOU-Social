package com.eneskayiklik.eventverse.feature.profile.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.eneskayiklik.eventverse.R

@Composable
fun ProfileImage(
    picUrl: String,
    modifier: Modifier = Modifier
) {
    if (picUrl.isNotEmpty())
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(picUrl)
                .crossfade(true)
                .build(),
            loading = {
                EmptyImageBox(modifier)
            },
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .clip(CircleShape)
        ) else EmptyImageBox(modifier, showProgress = false)
}

@Composable
private fun EmptyImageBox(modifier: Modifier = Modifier, showProgress: Boolean = true) {
    Box(
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
        if (showProgress)
            CircularProgressIndicator(
                strokeWidth = 2.dp,
                modifier = Modifier.fillMaxSize(.3F)
            )
    }
}