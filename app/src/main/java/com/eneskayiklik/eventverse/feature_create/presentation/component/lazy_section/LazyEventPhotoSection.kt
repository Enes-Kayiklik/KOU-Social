package com.eneskayiklik.eventverse.feature_create.presentation.component.lazy_section

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.feature_create.presentation.CreateViewModel
import com.eneskayiklik.eventverse.feature_create.presentation.component.HeaderSection
import com.eneskayiklik.eventverse.feature_create.presentation.util.CreateState

@ExperimentalAnimationApi
@ExperimentalFoundationApi
fun LazyListScope.eventPhotoSection(
    coverImage: Uri?,
    viewModel: CreateViewModel
) {
    item {
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
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) {
            if (it != null)
                viewModel.onCreateState(CreateState.OnImageSelected(it))
        }

        if (coverImage != null) {
            SelectedImageView(coverImage) {
                launcher.launch("image/*")
            }
        } else {
            SelectImageView {
                launcher.launch("image/*")
            }
        }
    }
}


@Composable
private fun SelectedImageView(coverImage: Uri, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .padding(16.dp)
            .clickable { onClick() }, contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = rememberImagePainter(data = coverImage, builder = {
                crossfade(true)
                size(OriginalSize)
            }),
            contentScale = ContentScale.FillWidth,
            contentDescription = null
        )
    }
}

@Composable
private fun SelectImageView(onClick: () -> Unit) {
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
            .clickable { onClick() }, contentAlignment = Alignment.Center
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