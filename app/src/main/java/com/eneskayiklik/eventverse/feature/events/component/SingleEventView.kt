package com.eneskayiklik.eventverse.feature.events.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.data.model.create.Event

@Composable
fun SingleEventView(
    event: Event,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        EventImage(image = event.coverImage, month = event.month, day = event.day)
        Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {
            Text(
                text = event.startDate, style = MaterialTheme.typography.h1.copy(
                    color = MaterialTheme.colors.onSecondary,
                    fontSize = 12.sp
                )
            )
            Text(
                text = event.title, style = MaterialTheme.typography.h1.copy(
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 18.sp
                )
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (event.totalAttendeeCount > 0)
                    Text(
                        text = stringResource(
                            id = R.string.attendee_count,
                            event.totalAttendeeCount
                        ), style = MaterialTheme.typography.h1.copy(
                            color = MaterialTheme.colors.onSecondary,
                            fontSize = 12.sp
                        )
                    )

                if (event.showIndicator)
                    Divider(
                        modifier = Modifier
                            .height(10.dp)
                            .width(1.dp)
                            .background(MaterialTheme.colors.onSecondary)
                    )

                if (event.likedByPlusCount > 0)
                    Text(
                        text = stringResource(
                            id = R.string.like_count,
                            event.likedByPlusCount
                        ), style = MaterialTheme.typography.h1.copy(
                            color = MaterialTheme.colors.onSecondary,
                            fontSize = 12.sp
                        )
                    )
            }
        }
    }
}

@Composable
private fun EventImage(
    image: String,
    month: String,
    day: String
) {
    if (image.isNotEmpty()) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .crossfade(true)
                .build(),
            loading = {

            },
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth(.4F)
                .height(100.dp)
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth(.4F)
                .height(100.dp)
                .background(MaterialTheme.colors.secondary),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = month, style = MaterialTheme.typography.h1.copy(
                    color = MaterialTheme.colors.onSecondary,
                    fontSize = 12.sp
                )
            )
            Text(
                text = day, style = MaterialTheme.typography.h1.copy(
                    color = MaterialTheme.colors.onSecondary,
                    fontSize = 24.sp
                )
            )
        }
    }
}