package com.eneskayiklik.eventverse.feature_auth.data.repository

import com.eneskayiklik.eventverse.core.util.Settings
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.User

class StreamRepositoryImpl(
    private val client: ChatClient
) {

    fun connectUser() {
        val appUser = Settings.currentUser
        val user = User(id = appUser.userId).apply {
            name = appUser.fullName
            image = appUser.profilePic
        }
        client.connectUser(
            user = user,
            token = client.devToken(appUser.userId)
        ).enqueue()
    }
}