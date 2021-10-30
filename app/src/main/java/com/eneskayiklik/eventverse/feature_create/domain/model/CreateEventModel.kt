package com.eneskayiklik.eventverse.feature_create.domain.model

data class CreateEventModel(
    val title: String,
    val description: String,
    val startTime: Long,
    val endTime: Long,
    val location: String,
    val coverImage: String
)
