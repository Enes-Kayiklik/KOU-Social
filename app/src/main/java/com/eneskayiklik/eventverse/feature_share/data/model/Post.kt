package com.eneskayiklik.eventverse.feature_share.data.model

import com.eneskayiklik.eventverse.feature_auth.data.model.PostUser
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
