package com.eneskayiklik.eventverse.feature.auth.introduction.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

fun getIntroTitleString(
    onBackgroundColor: Color,
    primaryColor: Color,
    subtitleColor: Color,
    appName: String,
    welcome: String,
    desc: String
) = buildAnnotatedString {
    withStyle(SpanStyle(color = onBackgroundColor, fontSize = 24.sp)) {
        append(welcome)
    }
    append("\n")
    withStyle(SpanStyle(color = primaryColor, fontSize = 36.sp)) {
        append(appName)
    }
    append("\n")
    withStyle(SpanStyle(color = subtitleColor, fontSize = 16.sp)) {
        append(desc)
    }
}