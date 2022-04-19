package com.eneskayiklik.eventverse.data.state.poll

import com.eneskayiklik.eventverse.data.model.poll.Poll

data class PollsState(
    val polls: List<Poll> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val hasNext: Boolean = true
) {
    val showInitialLoading = isLoading && polls.isEmpty()
    val showEmptyScreen = hasNext.not() && polls.isEmpty() && isLoading.not()

    // This is for force update to state flow
    override fun equals(other: Any?): Boolean {
        return false
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}
