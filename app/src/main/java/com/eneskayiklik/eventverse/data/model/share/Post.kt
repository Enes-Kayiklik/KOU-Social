package com.eneskayiklik.eventverse.data.model.share

import com.eneskayiklik.eventverse.data.model.auth.PostUser
import com.eneskayiklik.eventverse.util.Settings
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import java.text.SimpleDateFormat
import java.util.*

data class Post(
    val id: String = "",
    val body: String = "",
    val image: String = "",
    val createdAt: Timestamp = Timestamp(Date(System.currentTimeMillis())),
    val fromUser: PostUser = PostUser()
) {
    val formattedDate: String =
        SimpleDateFormat("dd MMM hh:mm", Locale.getDefault()).format(createdAt.toDate())
}

data class PostDto(
    @DocumentId
    val id: String = "",
    val createdAt: Timestamp = Timestamp(Date(System.currentTimeMillis())),
    val image: String = "",
    val body: String = "",
    val userId: String = ""
) {
    fun toPost() = Post(
        id = id,
        body = body,
        image = image,
        createdAt = createdAt,
        fromUser = Settings.userStorage.first { it.userId == userId }
    )
}
