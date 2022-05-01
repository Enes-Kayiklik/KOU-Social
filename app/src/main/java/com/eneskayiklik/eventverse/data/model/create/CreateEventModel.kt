package com.eneskayiklik.eventverse.data.model.create

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class CreateEventModel(
    @DocumentId
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val startTime: Timestamp = Timestamp.now(),
    val endTime: Timestamp = Timestamp.now(),
    val location: String = "",
    val coverImage: String = ""
)
