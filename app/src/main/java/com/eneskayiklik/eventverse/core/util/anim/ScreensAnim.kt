package com.eneskayiklik.eventverse.core.util.anim

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry
import com.eneskayiklik.eventverse.core.util.Screen

@ExperimentalAnimationApi
object ScreensAnim {
    private val BOTTOM_NAV_ITEMS = listOf(
        Screen.Home.route,
        Screen.MessageList.route,
        Screen.Profile.route(true),
        Screen.Announcement.route,
        Screen.Polls.route
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
                targetOffsetY = { (-it / 7) })
            else -> slideOutHorizontally(
                animationSpec = tween(300),
                targetOffsetX = { (-it / 7) })
        }
    }

    fun AnimatedContentScope<NavBackStackEntry>.popEnterTransition(): EnterTransition {
        return when (initialState.destination.route) {
            in BOTTOM_NAV_ITEMS -> fadeIn()
            Screen.CreateEvent.route, Screen.Share.route -> slideInVertically(
                animationSpec = tween(300),
                initialOffsetY = { -it })
            else -> slideInHorizontally(
                animationSpec = tween(300),
                initialOffsetX = { -it })
        }
    }

    fun AnimatedContentScope<NavBackStackEntry>.popExitTransition(): ExitTransition {
        return when (initialState.destination.route) {
            in BOTTOM_NAV_ITEMS -> fadeOut()
            Screen.CreateEvent.route, Screen.Share.route -> slideOutVertically(
                animationSpec = tween(300),
                targetOffsetY = { it / 7 })
            else -> slideOutHorizontally(animationSpec = tween(300), targetOffsetX = { it / 7 })
        }
    }

    fun fadeIn(): EnterTransition {
        return fadeIn(animationSpec = tween(300))
    }

    fun fadeOut(): ExitTransition {
        return fadeOut(animationSpec = tween(300))
    }
}