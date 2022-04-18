package com.eneskayiklik.eventverse.data.model.share

import com.eneskayiklik.eventverse.data.model.auth.PostUser
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import java.util.*

data class Post(
    @DocumentId
    val id: String = "",
    val createdAt: Timestamp = Timestamp(Date(System.currentTimeMillis())),
    val title: String = "",
    val body: String = "",
    val fromUser: PostUser = PostUser()
)
