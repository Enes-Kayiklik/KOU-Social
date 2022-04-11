package com.eneskayiklik.eventverse.feature_announcement.data.state

import com.eneskayiklik.eventverse.feature_announcement.data.model.Announcement

data class AnnouncementState(
    val announcements: List<Announcement> = emptyList(),
    val isLoading: Boolean = true,
    val showEmptyDepartmentView: Boolean = false,
    val isRefreshing: Boolean = false,
) {
    val showEmptyContent = showEmptyDepartmentView || announcements.isEmpty()
}
