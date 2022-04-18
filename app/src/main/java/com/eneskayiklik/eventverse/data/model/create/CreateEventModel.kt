package com.eneskayiklik.eventverse.data.model.create

data class CreateEventModel(
    val title: String,
    val description: String,
    val startTime: Long,
    val endTime: Long,
    val location: String,
    val coverImage: String
)
