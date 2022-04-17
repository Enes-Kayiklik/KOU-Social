package com.eneskayiklik.eventverse.feature_polls.data.state

data class CreatePollState(
    val title: String = "",
    val options: List<String> = listOf("", ""),
    val isLoading: Boolean = false
)
