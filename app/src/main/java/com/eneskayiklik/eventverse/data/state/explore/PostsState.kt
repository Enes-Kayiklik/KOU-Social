package com.eneskayiklik.eventverse.data.state.explore

import com.eneskayiklik.eventverse.data.model.share.Post

data class PostsState(
    val posts: List<Post> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val hasNext: Boolean = true
) {
    val showInitialLoading = isLoading && posts.isEmpty()
    val showEmptyScreen = hasNext.not() && posts.isEmpty() && isLoading.not()

    // This is for force update to state flow
    override fun equals(other: Any?): Boolean {
        return false
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}
