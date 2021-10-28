package com.eneskayiklik.eventverse.feature_create.presentation.component.lazy_section

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.feature_create.presentation.component.HeaderSection

@ExperimentalAnimationApi
@ExperimentalFoundationApi
fun LazyListScope.eventPhotoSection(

) {
    stickyHeader {
        HeaderSection(
            title = stringResource(id = R.string.event_photo),
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface)
                .padding(16.dp)
        )
        Divider(color = MaterialTheme.colors.background, thickness = 2.dp)
    }

    item {
        val stroke = Stroke(
            width = 4F,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(20F, 25F), 0f)
        )
        val primaryColor = MaterialTheme.colors.primary
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface)
                .padding(16.dp)
                .height(170.dp)
                .clickable { }, contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawRoundRect(
                    color = primaryColor,
                    style = stroke
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_select_photo),
                    contentDescription = stringResource(
                        id = R.string.select_image
                    ),
                    tint = primaryColor
                )
                Text(
                    text = stringResource(id = R.string.select_photo),
                    style = MaterialTheme.typography.h1
                )
            }
        }
    }
}