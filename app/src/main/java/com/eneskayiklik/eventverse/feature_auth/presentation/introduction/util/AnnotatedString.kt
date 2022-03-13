package com.eneskayiklik.eventverse.feature_auth.presentation.introduction.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

fun getIntroTitleString(
    onBackgroundColor: Color,
    primaryColor: Color,
    subtitleColor: Color
) = buildAnnotatedString {
    withStyle(SpanStyle(color = onBackgroundColor, fontSize = 24.sp)) {
        append("Welcome to")
    }
    append("\n")
    withStyle(SpanStyle(color = primaryColor, fontSize = 36.sp)) {
        append("KOU Event")
    }
    append("\n")
    withStyle(SpanStyle(color = subtitleColor, fontSize = 16.sp)) {
        append("A place where you can track all your expenses and incomes...")
    }
}