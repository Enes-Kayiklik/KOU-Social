package com.eneskayiklik.eventverse.feature.events.detail.lazy_items

import androidx.annotation.StringRes
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.data.state.events.RemainingDate

fun LazyListScope.detailImage(
    image: String,
    date: RemainingDate,
    showTimer: Boolean
) {
    item {
        val itemsColor = if (image.isEmpty()) MaterialTheme.colors.onSecondary else Color.White
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(MaterialTheme.colors.secondary)
        ) {
            EventImage(image = image)
            if (showTimer)
                EventDate(
                    date = date,
                    modifier = Modifier.align(Alignment.Center),
                    itemsColor = itemsColor
                )
        }
    }
}

@Composable
private fun EventDate(
    date: RemainingDate,
    itemsColor: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top
    ) {
        SingleDateBox(data = date.hour, title = R.string.hour, itemsColor = itemsColor)
        Text(
            text = ":",
            style = MaterialTheme.typography.h1.copy(
                fontSize = 48.sp,
                color = itemsColor
            ), modifier = Modifier.padding(horizontal = 5.dp)
        )
        SingleDateBox(data = date.minute, title = R.string.minute, itemsColor = itemsColor)
        Text(
            text = ":",
            style = MaterialTheme.typography.h1.copy(
                fontSize = 48.sp,
                color = itemsColor
            ), modifier = Modifier.padding(horizontal = 5.dp)
        )
        SingleDateBox(data = date.second, title = R.string.second, itemsColor = itemsColor)
    }
}

@OptIn(
    ExperimentalAnimationApi::class
)
@Composable
private fun SingleDateBox(
    data: List<Int>,
    itemsColor: Color,
    @StringRes title: Int,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.animateContentSize()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            data.forEach {
                AnimatedContent(
                    targetState = it,
                    transitionSpec = {
                        (slideInVertically { height -> -height } + fadeIn() with
                                slideOutVertically { height -> height } + fadeOut()).using(
                            SizeTransform(clip = false)
                        )
                    }
                ) { targetCount ->
                    Text(
                        text = "$targetCount",
                        style = MaterialTheme.typography.h1.copy(
                            fontSize = 48.sp,
                            color = itemsColor
                        )
                    )
                }
            }
        }
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.h4.copy(
                fontSize = 14.sp,
                color = itemsColor
            )
        )
    }
}

@Composable
private fun EventImage(image: String) {
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
            modifier = Modifier.fillMaxSize()
        )
    }
}