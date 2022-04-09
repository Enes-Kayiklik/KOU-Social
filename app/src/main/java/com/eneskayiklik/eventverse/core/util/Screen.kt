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
    object Profile : Screen("profile/{userId}") {
        fun route(isSelf: Boolean = false, userId: String = "") =
            if (isSelf) "profile/${Settings.currentUser.userId}" else "profile/$userId"
    }
    object CreateEvent : Screen("screen_event")
    object SettingsScreen : Screen("settings")
    object Share : Screen("share")
    object MessageList : Screen("message_list")
    object Messages : Screen("messages/{channelId}") {
        fun route(channelId: String) = "messages/$channelId"
    }
}
