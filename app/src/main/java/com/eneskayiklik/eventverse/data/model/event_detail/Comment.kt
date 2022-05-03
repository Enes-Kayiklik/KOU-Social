package com.eneskayiklik.eventverse.data.model.event_detail

import com.eneskayiklik.eventverse.data.model.auth.PostUser
import com.eneskayiklik.eventverse.util.Settings
import com.eneskayiklik.eventverse.util.extension.formatDate
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Comment(
    val id: String = "",
    val rating: Float = 0F,
    val comment: String = "",
    val createdAt: Timestamp = Timestamp.now(),
    val user: PostUser = PostUser()
) {
    fun toDto() = CommentDto(
        rating = rating,
        comment = comment,
        userId = user.userId,
        createdAt = createdAt
    )

    val formattedDate: String = createdAt.toDate().formatDate("MMMM dd, yyyy")
}

data class CommentDto(
    @DocumentId
    val id: String = "",
    val rating: Float = 0F,
    val comment: String = "",
    val createdAt: Timestamp = Timestamp.now(),
    val userId: String = ""
) {

    fun toComment() = Comment(
        id = id,
        rating = rating,
        comment = comment,
        createdAt = createdAt,
        user = Settings.userStorage.firstOrNull { it.userId == userId } ?: PostUser()
    )
}
