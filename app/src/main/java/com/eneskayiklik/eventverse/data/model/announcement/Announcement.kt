package com.eneskayiklik.eventverse.data.model.announcement

import java.text.SimpleDateFormat
import java.util.*

data class Announcement(
    val title: String = "",
    val date: String = "",
    val sender: String = "",
    val activeDateRange: String = "",
    val content: String = "",
    val attachment: String = "",
    val baseUrl: String = ""
) {
    val hasAttach = attachment.isNotBlank()
    private val attachType = if (attachment.contains("http")) AttachType.LINK else AttachType.EMBED
    val attachUri = if (attachType == AttachType.LINK) attachment else "$baseUrl$attachment"

    fun getFormattedDate(): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val newDate = formatter.parse(date) ?: return ""
        return SimpleDateFormat("dd MMM", Locale.getDefault()).format(newDate)
    }

    fun getFullDate(): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val newDate = formatter.parse(date) ?: return ""
        return SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(newDate)
    }
}

enum class AttachType {
    LINK,
    EMBED
}
