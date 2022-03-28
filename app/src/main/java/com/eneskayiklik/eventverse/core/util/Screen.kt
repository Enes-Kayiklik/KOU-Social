package com.eneskayiklik.eventverse.core.util

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Intro : Screen("intro")
    object Login : Screen("login")
    object Signup : Screen("signup")
    object SelectInterest : Screen("interest")
    object Explore : Screen("explore")
    object Calendar : Screen("calendar")
    object Map : Screen("map")
    object Profile : Screen("profile")
    object CreateEvent : Screen("screen_event")
    object Share : Screen("screen_share")
    object MessageList : Screen("screen_message_list")
    object Messages : Screen("screen_messages/{channelId}") {
        fun route(channelId: String) = "screen_messages/$channelId"
    }
}
