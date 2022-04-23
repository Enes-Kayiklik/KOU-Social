package com.eneskayiklik.eventverse.core.data.model

data class ErrorState(
    val title: String? = null,
    val subTitle: String? = null,
    val firstButtonText: String? = null,
    val secondButtonText: String? = null,
    val firstButtonClick: () -> Unit = {},
    val secondButtonClick: () -> Unit = {},
    val dismissOnBackPressed: Boolean = false,
    val dismissOnOutClick: Boolean = false
)