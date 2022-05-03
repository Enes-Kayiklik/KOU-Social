package com.eneskayiklik.eventverse.feature.events.detail

import android.app.Activity
import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.eneskayiklik.eventverse.core.presentation.MainActivity
import com.eneskayiklik.eventverse.feature.events.detail.lazy_items.detailImage
import com.eneskayiklik.eventverse.util.Screen
import com.eneskayiklik.eventverse.util.UiEvent
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.enterTransition
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.exitTransition
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.popEnterTransition
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.popExitTransition
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.flow.collectLatest

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalFoundationApi::class,
    ExperimentalAnimationApi::class
)
@Composable
private fun EventDetailScreen(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
    viewModel: EventDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val event = state.event
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.event.collectLatest {
            when (it) {
                is UiEvent.RestartApp -> {
                    context.startActivity(Intent(context, MainActivity::class.java))
                    (context as? Activity)?.finish()
                }
                is UiEvent.Navigate -> onNavigate(it.id)
                UiEvent.ClearBackStack -> clearBackStack()
                else -> Unit
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        if (state.isLoading) CircularProgressIndicator(
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.Center),
            color = MaterialTheme.colors.primary,
            strokeWidth = 2.dp
        ) else LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            if (event == null) return@LazyColumn
            detailImage(event.coverImage, state.date, clearBackStack)
        }
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.eventDetailComposable(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
) {
    composable(
        route = Screen.EventDetail.route,
        exitTransition = { exitTransition() },
        popExitTransition = { popExitTransition() },
        popEnterTransition = { popEnterTransition() },
        enterTransition = { enterTransition() },
        arguments = listOf(navArgument("eventId") { type = NavType.StringType })
    ) {
        EventDetailScreen(onNavigate, clearBackStack)
    }
}