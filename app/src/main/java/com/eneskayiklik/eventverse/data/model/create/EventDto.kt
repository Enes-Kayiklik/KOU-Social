package com.eneskayiklik.eventverse.data.model.create

import com.eneskayiklik.eventverse.data.model.auth.PostUser
import com.eneskayiklik.eventverse.util.Settings
import com.eneskayiklik.eventverse.util.extension.formatDate
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Event(
    val id: String = "",
    val user: PostUser = PostUser(),
    val attendee: List<PostUser> = emptyList(),
    val attendeePlusCount: Int = 0,
    val totalAttendeeCount: Int = 0,
    val likedBy: List<PostUser> = emptyList(),
    val likedByPlusCount: Int = 0,
    val title: String = "",
    val description: String = "",
    val fullDate: String = "",
    val startDate: String = "",
    val location: String = "",
    val coverImage: String = "",
    val createdAt: Timestamp = Timestamp.now(),
    val month: String = "",
    val day: String = ""
) {
    val showIndicator = totalAttendeeCount > 0 && likedByPlusCount > 0
}

data class EventDto(
    @DocumentId
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val startTime: Timestamp = Timestamp.now(),
    val endTime: Timestamp = Timestamp.now(),
    val location: String = "",
    val coverImage: String = "",
    val likedBy: List<String> = emptyList(),
    val attendee: List<String> = emptyList(),
    val createdAt: Timestamp = Timestamp.now(),
    val user: String = ""
) {
    /*private val totalAttendeeC = attendee.count()
    private val takenAttendee = attendee.shuffled().take(5)
    private val plusAttendeeCount =
        (totalAttendeeC - takenAttendee.count()).takeIf { it >= 0 } ?: 0*/

    fun toEvent() = Event(
        id = id,
        user = Settings.userStorage.firstOrNull { it.userId == user } ?: PostUser(),
        title = title,
        description = description,
        location = location,
        coverImage = coverImage,
        attendee = attendee.shuffled().take(5).map { m ->
            Settings.userStorage.firstOrNull { it.userId == m } ?: PostUser()
        },
        attendeePlusCount = (attendee.count() - minOf(5, attendee.count())).takeIf { it >= 0 } ?: 0,
        totalAttendeeCount = attendee.count(),
        fullDate = "${
            startTime.toDate().formatDate("EEEE, MMM dd, yyyy HH:mm")
        } - ${endTime.toDate().formatDate("EEEE, MMM dd, yyyy HH:mm")}",
        startDate = startTime.toDate().formatDate("EE, MMM dd, yyyy HH:mm").uppercase(),
        month = startTime.toDate().formatDate("MMM").uppercase(),
        day = startTime.toDate().formatDate("dd"),
        createdAt = createdAt,
        likedByPlusCount = likedBy.count()
    )
}
