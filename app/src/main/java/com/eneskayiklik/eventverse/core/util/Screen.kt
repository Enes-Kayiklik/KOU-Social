package com.eneskayiklik.eventverse.core.util

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Signup : Screen("signup")
    object Timeline : Screen("timeline")
}
