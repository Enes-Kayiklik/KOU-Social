package com.eneskayiklik.eventverse.data.state.announcement

import com.eneskayiklik.eventverse.data.model.announcement.Announcement

data class AnnouncementState(
    val announcements: List<Announcement> = emptyList(),
    val isLoading: Boolean = true,
    val showEmptyDepartmentView: Boolean = false,
    val isRefreshing: Boolean = false,
    val isPopupActive: Boolean = false,
    val activeAnnouncement: Announcement = Announcement()
) {
    val showEmptyContent = showEmptyDepartmentView || announcements.isEmpty()
}
