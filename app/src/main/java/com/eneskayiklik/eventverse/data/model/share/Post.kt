package com.eneskayiklik.eventverse.data.model.share

import com.eneskayiklik.eventverse.data.model.auth.PostUser
import com.eneskayiklik.eventverse.util.Settings
import com.eneskayiklik.eventverse.util.extension.formatDate
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import java.util.*

data class Post(
    val id: String = "",
    val body: String = "",
    val image: String = "",
    val likeCount: Int = 0,
    val isUserLike: Boolean = false,
    val createdAt: Timestamp = Timestamp(Date(System.currentTimeMillis())),
    val fromUser: PostUser = PostUser()
) {
    val formattedDate: String = createdAt.toDate().formatDate()
}

data class PostDto(
    @DocumentId
    val id: String = "",
    val createdAt: Timestamp = Timestamp(Date(System.currentTimeMillis())),
    val image: String = "",
    val body: String = "",
    val userId: String = "",
    val likedBy: List<String> = emptyList()
) {
    fun toPost() = Post(
        id = id,
        body = body,
        image = image,
        likeCount = likedBy.count(),
        createdAt = createdAt,
        isUserLike = Settings.currentUser.userId in likedBy,
        fromUser = Settings.userStorage.firstOrNull { it.userId == userId } ?: PostUser()
    )
}
