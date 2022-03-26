package com.eneskayiklik.eventverse.feature_message.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.core.util.Screen
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.enterTransition
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.exitTransition
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.popEnterTransition
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.popExitTransition
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.navigation.animation.composable
import io.getstream.chat.android.compose.ui.channels.ChannelsScreen
import io.getstream.chat.android.compose.ui.theme.ChatTheme

@Composable
private fun MessageListScreen(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
) {
    ChatTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .statusBarsPadding()
        ) {
            ChannelsScreen(
                title = stringResource(id = R.string.app_name),
                onItemClick = { channel ->
                    // TODO Start Messages Activity
                },
                onBackPressed = { clearBackStack() }
            )
        }
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.messageListComposable(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
) {
    composable(
        route = Screen.MessageList.route,
        exitTransition = { exitTransition() },
        popExitTransition = { popExitTransition() },
        popEnterTransition = { popEnterTransition() },
        enterTransition = { enterTransition() },
    ) {
        MessageListScreen(onNavigate, clearBackStack)
    }
}