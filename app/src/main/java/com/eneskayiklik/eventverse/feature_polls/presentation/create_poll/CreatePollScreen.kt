package com.eneskayiklik.eventverse.feature_polls.presentation.create_poll

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.core.util.Screen
import com.eneskayiklik.eventverse.core.util.UiEvent
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.enterTransition
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.exitTransition
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.popEnterTransition
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.popExitTransition
import com.eneskayiklik.eventverse.feature_auth.presentation.login.component.LoginButton
import com.eneskayiklik.eventverse.feature_polls.presentation.component.CreatePollTitleField
import com.eneskayiklik.eventverse.feature_polls.presentation.component.CreatePollToolbar
import com.eneskayiklik.eventverse.feature_polls.presentation.component.OneRowPollOption
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.flow.collectLatest

@OptIn(
    ExperimentalAnimationApi::class
)
@Composable
private fun CreatePollScreen(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
    viewModel: CreatePollViewModel = hiltViewModel()
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
            CreatePollToolbar(
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
                contentPadding = PaddingValues(vertical = 20.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    CreatePollTitleField(
                        state.title,
                        viewModel::onTitleChange
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
                items(state.options.count(), key = { it }) {
                    val item = state.options[it]
                    val lastIndex = state.options.lastIndex
                    OneRowPollOption(
                        text = item,
                        placeholder = stringResource(id = R.string.poll_placeholder, it + 1),
                        showPlusSign = it == lastIndex,
                        onEndIconClick = { isAdd -> viewModel.onRemoveOrAdd(isAdd, it) }
                    ) { text ->
                        viewModel.onOptionChanged(text, it)
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
            viewModel.sharePoll()
        }
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.createPollComposable(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
) {
    composable(
        route = Screen.CreatePoll.route,
        exitTransition = { exitTransition() },
        popExitTransition = { popExitTransition() },
        popEnterTransition = { popEnterTransition() },
        enterTransition = { enterTransition() },
    ) {
        CreatePollScreen(onNavigate, clearBackStack)
    }
}