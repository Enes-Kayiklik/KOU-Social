package com.eneskayiklik.eventverse.core.component.cropper

import android.graphics.Bitmap
import android.net.Uri
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.canhub.cropper.CropImageView
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.core.data.event.CropperEvent
import com.google.accompanist.insets.statusBarsPadding
import kotlin.math.abs

@Composable
fun CropperView(
    modifier: Modifier = Modifier,
    viewModel: CropperViewModel = hiltViewModel(),
    bitmap: Bitmap? = null,
    uri: Uri? = null,
    ratio: List<Int>? = null,
    onCropEvent: (CropperEvent) -> Unit,
) {
    val state = viewModel.cropperState.collectAsState().value
    var isCroppingStart by remember { mutableStateOf(false) }

    BackHandler {
        onCropEvent(CropperEvent.OnCropCancel)
    }

    if (state.isCropping) CircularProgressIndicator()
    Column(modifier = modifier) {
        CropperToolbar(
            onBackPress = { onCropEvent(CropperEvent.OnCropCancel) },
            onCropPress = { viewModel.onEvent(CropperEvent.OnCroppingEvent(true)) },
            onRotatePress = { viewModel.onEvent(CropperEvent.OnRotation) }
        )
        AndroidView(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface),
            factory = { context ->
                FrameLayout(context).apply {
                    addView(CropImageView(context).apply {
                        layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                        if (bitmap != null) setImageBitmap(bitmap) else if (uri != null) setImageUriAsync(
                            uri
                        )
                        if (ratio?.getOrNull(0) != null && ratio.getOrNull(1) != null) {
                            setAspectRatio(ratio[0], ratio[1])
                        }
                    })

                }
            }, update = {
                if (it.childCount > 0)
                    (it.getChildAt(0) as? CropImageView)?.apply {
                        // when rotate button is clicked and image is not cropping then rotate
                        if (state.rotation != 0F && state.isCropping.not()) rotateImage(
                            90
                        )
                        // when crop button clicked and cropping not start then start crop
                        if (state.isCropping && isCroppingStart.not()) {
                            isCroppingStart = true
                            val width = abs((cropRect?.left ?: 0) - (cropRect?.right ?: 0))
                            val height = abs((cropRect?.top ?: 0) - (cropRect?.bottom ?: 0))
                            viewModel.onEvent(CropperEvent.OnCroppingEvent(false))
                            onCropEvent(
                                CropperEvent.OnCropFinish(
                                    context,
                                    getCroppedImage(width, height)
                                )
                            )
                        }
                    }
            })
    }
}

@Composable
fun CropperToolbar(
    onBackPress: () -> Unit,
    onRotatePress: () -> Unit,
    onCropPress: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .statusBarsPadding()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onBackPress) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
                tint = MaterialTheme.colors.onBackground
            )
        }

        Row {
            IconButton(onClick = onRotatePress) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_rotate),
                    contentDescription = null,
                    tint = MaterialTheme.colors.onBackground
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            IconButton(onClick = onCropPress) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_done),
                    contentDescription = null,
                    tint = MaterialTheme.colors.onBackground
                )
            }
        }
    }
}