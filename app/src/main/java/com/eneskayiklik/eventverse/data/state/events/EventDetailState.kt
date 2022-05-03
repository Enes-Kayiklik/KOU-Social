package com.eneskayiklik.eventverse.data.state.events

import com.eneskayiklik.eventverse.data.model.create.Event

data class EventDetailState(
    val event: Event? = null,
    val isLoading: Boolean = true,
    val date: RemainingDate = RemainingDate()
)

data class RemainingDate(
    val hour: List<Int> = emptyList(),
    val minute: List<Int> = emptyList(),
    val second: List<Int> = emptyList()
)