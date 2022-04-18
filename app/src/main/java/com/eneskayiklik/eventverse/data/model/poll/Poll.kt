package com.eneskayiklik.eventverse.data.model.poll

import com.eneskayiklik.eventverse.data.model.auth.PostUser
import com.eneskayiklik.eventverse.util.Settings
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import java.util.*
import kotlin.collections.HashMap

data class Poll(
    val id: String = "",
    val title: String = "",
    val options: List<String> = listOf(),
    val answers: HashMap<String, Int> = hashMapOf(),
    val createdAt: Timestamp = Timestamp(Date(System.currentTimeMillis())),
    val fromUser: PostUser = PostUser()
) {
    private val answerSize = answers.count()
    val percentages = options.indices.map { indices ->
        (answers.count { it.value == indices } * 100) / answerSize
    }
}

data class PollDto(
    @DocumentId
    val id: String = "",
    val title: String = "",
    val options: List<String> = listOf(),
    val answers: HashMap<String, Int> = hashMapOf(),
    val createdAt: Timestamp = Timestamp(Date(System.currentTimeMillis())),
    val userId: String = ""
) {
    fun toPoll() = Poll(
        id = id,
        title = title,
        options = options,
        answers = answers,
        createdAt = createdAt,
        fromUser = Settings.userStorage.first { it.userId == userId }
    )
}