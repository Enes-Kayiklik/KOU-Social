package com.eneskayiklik.eventverse.util

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Intro : Screen("intro")
    object Login : Screen("login")
    object Signup : Screen("signup")
    object Home : Screen("home")
    object Events : Screen("events")
    object Polls : Screen("polls")
    object CreatePoll : Screen("create_poll")
    object Announcement : Screen("announcement")
    object Meal : Screen("meal")
    object Profile : Screen("profile/{userId}") {
        fun route(isSelf: Boolean = false, userId: String = "") =
            if (isSelf) "profile/${Settings.currentUser.userId}" else "profile/$userId"
    }
    object CreateEvent : Screen("screen_event")
    object SettingsScreen : Screen("settings")
    object EditProfile : Screen("edit_profile")
    object Share : Screen("share")
    object UpdatePassword : Screen("update_password")
    object DeleteAccount : Screen("delete_account")
    object Verify : Screen("verify")
}
