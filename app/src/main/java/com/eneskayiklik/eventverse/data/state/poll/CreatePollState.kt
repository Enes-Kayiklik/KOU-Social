package com.eneskayiklik.eventverse.data.state.poll

data class CreatePollState(
    val title: String = "",
    val options: List<String> = listOf("", ""),
    val isLoading: Boolean = false
)
