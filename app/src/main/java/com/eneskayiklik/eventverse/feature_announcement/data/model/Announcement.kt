package com.eneskayiklik.eventverse.feature_announcement.data.model

import java.text.SimpleDateFormat
import java.util.*

data class Announcement(
    val title: String = "",
    val date: String = "",
    val sender: String = "",
    val content: AnnouncementContent = AnnouncementContent()
) {
    val hasAttach = content.attachment.isNotBlank()

    fun getFormattedDate(): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val newDate = formatter.parse(date) ?: return ""
        return SimpleDateFormat("dd MMM", Locale.getDefault()).format(newDate)
    }
}

data class AnnouncementContent(
    val activeDateRange: String = "",
    val content: String = "",
    val attachment: String = ""
)
