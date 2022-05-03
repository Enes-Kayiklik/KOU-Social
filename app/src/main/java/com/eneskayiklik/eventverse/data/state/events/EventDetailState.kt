package com.eneskayiklik.eventverse.data.state.events

import com.eneskayiklik.eventverse.data.model.create.Event
import com.eneskayiklik.eventverse.data.model.event_detail.Comment
import com.eneskayiklik.eventverse.util.Settings

data class EventDetailState(
    val event: Event? = null,
    val comments: List<Comment> = emptyList(),
    val userComment: Comment = Comment(
        id = Settings.currentUser.userId,
        user = Settings.currentUser.toPostUser()
    ),
    val isLoading: Boolean = true,
    val isReviewsLoading: Boolean = false,
    val date: RemainingDate = RemainingDate()
) {
    val showTimer = isLoading.not() && date.second.isNotEmpty()
}

data class RemainingDate(
    val hour: List<Int> = emptyList(),
    val minute: List<Int> = emptyList(),
    val second: List<Int> = emptyList()
)