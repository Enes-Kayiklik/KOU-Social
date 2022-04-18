package com.eneskayiklik.eventverse.data.model.poll

import com.eneskayiklik.eventverse.data.model.auth.PostUser
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import java.util.*
import kotlin.collections.HashMap

data class Poll(
    @DocumentId
    val id: String = "",
    val title: String,
    val options: List<String> = listOf(),
    val answers: HashMap<String, Int> = hashMapOf(),
    val createdAt: Timestamp = Timestamp(Date(System.currentTimeMillis())),
    val fromUser: PostUser = PostUser()
)
