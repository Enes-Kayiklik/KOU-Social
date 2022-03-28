package com.eneskayiklik.eventverse.core.util.anim

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry
import com.eneskayiklik.eventverse.core.util.Screen

@ExperimentalAnimationApi
object ScreensAnim {
    private val BOTTOM_NAV_ITEMS = listOf(
        Screen.Explore.route,
        Screen.MessageList.route,
        Screen.Profile.route(true),
        Screen.Map.route,
        Screen.Calendar.route
    )

    fun AnimatedContentScope<NavBackStackEntry>.enterTransition(): EnterTransition {
        return when (targetState.destination.route) {
            in BOTTOM_NAV_ITEMS -> fadeIn()
            Screen.CreateEvent.route, Screen.Share.route -> slideInVertically(
                animationSpec = tween(300),
                initialOffsetY = { it })
            else -> slideInHorizontally(animationSpec = tween(300), initialOffsetX = { it })
        }
    }

    fun AnimatedContentScope<NavBackStackEntry>.exitTransition(): ExitTransition {
        return when (targetState.destination.route) {
            in BOTTOM_NAV_ITEMS -> fadeOut()
            Screen.CreateEvent.route, Screen.Share.route -> slideOutVertically(
                animationSpec = tween(300),
                targetOffsetY = { (-it / 3) * 2 }) + fadeOut(
                animationSpec = tween(durationMillis = 300), targetAlpha = 0.3F
            )
            else -> slideOutHorizontally(
                animationSpec = tween(300),
                targetOffsetX = { (-it / 3) * 2 }) + fadeOut(
                animationSpec = tween(durationMillis = 300), targetAlpha = 0.3F
            )
        }
    }

    fun AnimatedContentScope<NavBackStackEntry>.popEnterTransition(): EnterTransition {
        return when (initialState.destination.route) {
            in BOTTOM_NAV_ITEMS -> fadeIn()
            Screen.CreateEvent.route, Screen.Share.route -> slideInVertically(
                animationSpec = tween(300),
                initialOffsetY = { -it }) + fadeIn(
                animationSpec = tween(durationMillis = 300), initialAlpha = 0.3F
            )
            else -> slideInHorizontally(
                animationSpec = tween(300),
                initialOffsetX = { -it }) + fadeIn(
                animationSpec = tween(durationMillis = 300), initialAlpha = 0.3F
            )
        }
    }

    fun AnimatedContentScope<NavBackStackEntry>.popExitTransition(): ExitTransition {
        return when (initialState.destination.route) {
            in BOTTOM_NAV_ITEMS -> fadeOut()
            Screen.CreateEvent.route, Screen.Share.route -> slideOutVertically(
                animationSpec = tween(300),
                targetOffsetY = { it })
            else -> slideOutHorizontally(animationSpec = tween(300), targetOffsetX = { it })
        }
    }

    fun fadeIn(): EnterTransition {
        return fadeIn(animationSpec = tween(300))
    }

    fun fadeOut(): ExitTransition {
        return fadeOut(animationSpec = tween(300))
    }
}