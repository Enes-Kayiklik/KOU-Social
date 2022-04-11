package com.eneskayiklik.eventverse.feature_meal.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.eneskayiklik.eventverse.core.util.Screen
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.enterTransition
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.exitTransition
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.popEnterTransition
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.popExitTransition
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState

@Composable
private fun MealScreen(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
    viewModel: MealViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        if (state.connectionUrl.isNotEmpty()) {
            var scale by remember { mutableStateOf(1f) }
            val webViewState = rememberWebViewState(state.connectionUrl)
            WebView(state = webViewState, modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleY = scale
                    scaleX = scale
                }
                .pointerInput(Unit) {
                    detectTransformGestures { _, _, zoom, _ ->
                        scale = when {
                            scale < 1F -> 1F
                            scale > 3f -> 3f
                            else -> scale * zoom
                        }
                    }
                })
        } else {
            CircularProgressIndicator()
        }
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.mealComposable(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
) {
    composable(
        route = Screen.Meal.route,
        exitTransition = { exitTransition() },
        popExitTransition = { popExitTransition() },
        popEnterTransition = { popEnterTransition() },
        enterTransition = { enterTransition() },
    ) {
        MealScreen(onNavigate, clearBackStack)
    }
}