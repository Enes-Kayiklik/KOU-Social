package com.eneskayiklik.eventverse.core.util

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Signup : Screen("signup")
    object SelectInterest : Screen("interest")
    object Explore : Screen("explore")
    object Calendar : Screen("calendar")
    object Map : Screen("map")
    object Profile : Screen("profile")
    object CreateEvent : Screen("screen_event")
}
