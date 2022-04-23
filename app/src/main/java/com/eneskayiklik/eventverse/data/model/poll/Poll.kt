package com.eneskayiklik.eventverse.data.model.poll

import com.eneskayiklik.eventverse.data.model.auth.PostUser
import com.eneskayiklik.eventverse.util.Settings
import com.eneskayiklik.eventverse.util.extension.formatDate
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
    private val answerSize = answers.count().takeIf { it > 0 } ?: 1
    val percentages = options.indices.map { indices ->
        (answers.count { it.value == indices } * 100) / answerSize
    }

    val userAnswer = answers[Settings.currentUser.userId] ?: -1
    val showAnswers = userAnswer != -1

    val formattedDate: String = createdAt.toDate().formatDate()
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