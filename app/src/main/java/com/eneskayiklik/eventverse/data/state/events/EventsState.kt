package com.eneskayiklik.eventverse.data.state.events

import com.eneskayiklik.eventverse.data.model.create.Event

data class EventsState(
    val events: List<Event> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val hasNext: Boolean = true
) {
    val showInitialLoading = isLoading && events.isEmpty()
    val showEmptyScreen = hasNext.not() && events.isEmpty() && isLoading.not()

    // This is for force update to state flow
    override fun equals(other: Any?): Boolean {
        return false
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}
