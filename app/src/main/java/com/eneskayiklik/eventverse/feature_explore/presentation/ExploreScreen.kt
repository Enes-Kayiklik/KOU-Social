package com.eneskayiklik.eventverse.feature_explore.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.core.util.Screen
import com.google.accompanist.navigation.animation.composable

@Composable
private fun ExploreScreen(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(150.dp),
            painter = painterResource(id = R.drawable.ic_google_logo),
            contentDescription = ""
        )
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.exploreComposable(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
) {
    composable(
        route = Screen.Explore.route,
        enterTransition = { initial, _ ->
            val comingFrom = initial.destination.route ?: ""
            val condition =
                comingFrom.startsWith(Screen.Login.route) || comingFrom.startsWith(Screen.Splash.route) || comingFrom.startsWith(
                    Screen.SelectInterest.route
                )
            if (condition)
                slideInVertically(
                    initialOffsetY = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = 300)
                ) else null
        }
    ) {
        ExploreScreen(onNavigate, clearBackStack)
    }
}