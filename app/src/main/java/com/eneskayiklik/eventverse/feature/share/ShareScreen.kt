package com.eneskayiklik.eventverse.feature.share

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.IosShare
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.core.component.ExtendedTextField
import com.eneskayiklik.eventverse.util.Screen
import com.eneskayiklik.eventverse.util.UiEvent
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.enterTransition
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.exitTransition
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.popEnterTransition
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.popExitTransition
import com.eneskayiklik.eventverse.feature.explore.component.EventverseAppbar
import com.eneskayiklik.eventverse.data.event.share.ShareEvent
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ShareScreen(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
    scaffoldState: ScaffoldState,
    viewModel: ShareViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(key1 = true) {
        viewModel.event.collectLatest {
            when (it) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = it.message,
                        actionLabel = "OK"
                    )
                }
                is UiEvent.Navigate -> {
                    onNavigate(it.id)
                }
                UiEvent.ClearBackStack -> clearBackStack()
            }
        }
    }

    Scaffold(
        topBar = {
            EventverseAppbar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.primary)
                    .statusBarsPadding()
                    .height(60.dp),
                title = stringResource(id = R.string.create_event),
                startIcon = Icons.Rounded.ArrowBack,
                onStartIconClick = clearBackStack,
                endIcon = Icons.Rounded.IosShare,
                onEndIconClick = viewModel::sharePost
            )
        }, modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(10.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
            ExtendedTextField(
                text = state.titleState.text,
                onValueChange = {
                    viewModel.onEvent(
                        ShareEvent.OnTitle(it)
                    )
                },
                error = state.titleState.error,
                placeholder = stringResource(id = R.string.email_paceholder),
                keyboardType = KeyboardType.Text,
                modifier = Modifier.fillMaxWidth().weight(1F)
            )

            ExtendedTextField(
                text = state.bodyState.text,
                onValueChange = {
                    viewModel.onEvent(
                        ShareEvent.OnBody(it)
                    )
                },
                error = state.bodyState.error,
                placeholder = stringResource(id = R.string.email_paceholder),
                keyboardType = KeyboardType.Text,
                modifier = Modifier.fillMaxWidth().weight(9F)
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.shareComposable(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
    scaffoldState: ScaffoldState,
) {
    composable(
        route = Screen.Share.route,
        exitTransition = { exitTransition() },
        popExitTransition = { popExitTransition() },
        popEnterTransition = { popEnterTransition() },
        enterTransition = { enterTransition() },
    ) {
        ShareScreen(onNavigate, clearBackStack, scaffoldState)
    }
}