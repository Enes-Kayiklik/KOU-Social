package com.eneskayiklik.eventverse.feature.share

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.core.component.cropper.CropperView
import com.eneskayiklik.eventverse.util.Screen
import com.eneskayiklik.eventverse.util.UiEvent
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.enterTransition
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.exitTransition
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.popEnterTransition
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.popExitTransition
import com.eneskayiklik.eventverse.data.event.share.ShareEvent
import com.eneskayiklik.eventverse.feature.auth.login.component.LoginButton
import com.eneskayiklik.eventverse.feature.share.component.ShareContentField
import com.eneskayiklik.eventverse.feature.share.component.SharePhotoSection
import com.eneskayiklik.eventverse.feature.share.component.ShareToolbar
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.flow.collectLatest

@OptIn(
    ExperimentalAnimationApi::class
)
@Composable
fun ShareScreen(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
    viewModel: ShareViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(key1 = true) {
        viewModel.event.collectLatest {
            when (it) {
                is UiEvent.Navigate -> {
                    onNavigate(it.id)
                }
                UiEvent.ClearBackStack -> clearBackStack()
                else -> Unit
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            ShareToolbar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.background)
                    .statusBarsPadding()
                    .height(56.dp),
                onStartIconClick = {
                    clearBackStack()
                }
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                contentPadding = PaddingValues(
                    top = 20.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 72.dp
                ),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    SharePhotoSection(
                        coverImage = state.postImage,
                        onShareEvent = viewModel::onEvent
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
                item {
                    ShareContentField(text = state.bodyState.text) {
                        viewModel.onEvent(ShareEvent.OnBody(it))
                    }
                }
            }
        }
        LoginButton(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(44.dp)
                .align(Alignment.BottomCenter),
            clicked = state.isLoading,
            text = stringResource(id = R.string.share_poll)
        ) {
            viewModel.sharePost()
        }
    }
    AnimatedVisibility(visible = state.selectedImage.isNotEmpty(),
        enter = slideInHorizontally(initialOffsetX = { it }),
        exit = slideOutHorizontally(targetOffsetX = { it })
    ) {
        CropperView(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
            uri = Uri.parse(state.selectedImage),
            onCropEvent = viewModel::onCropperEvent)
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.shareComposable(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit
) {
    composable(
        route = Screen.Share.route,
        exitTransition = { exitTransition() },
        popExitTransition = { popExitTransition() },
        popEnterTransition = { popEnterTransition() },
        enterTransition = { enterTransition() },
    ) {
        ShareScreen(onNavigate, clearBackStack)
    }
}