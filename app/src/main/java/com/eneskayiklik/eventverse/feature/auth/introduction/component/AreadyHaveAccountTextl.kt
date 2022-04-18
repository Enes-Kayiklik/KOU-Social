package com.eneskayiklik.eventverse.feature.auth.introduction.component

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

@Composable
fun AlreadyHaveAccountText(
    modifier: Modifier = Modifier,
    leadingText: String,
    clickableText: String,
    onClick: () -> Unit
) {
    val annotatedString = buildAnnotatedString {
        withStyle(SpanStyle(color = MaterialTheme.colors.onBackground)) {
            append(leadingText)
        }
        append(" ")
        withStyle(
            SpanStyle(
                color = MaterialTheme.colors.primary,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append(clickableText)
        }
        addStringAnnotation(
            tag = "login",
            annotation = "login",
            start = leadingText.length - 1,
            end = leadingText.length * 2
        )
    }

    ClickableText(
        modifier = modifier,
        text = annotatedString,
        style = MaterialTheme.typography.caption.copy(fontSize = 16.sp),
        onClick = { offset ->
            annotatedString.getStringAnnotations(
                tag = "login",
                start = offset,
                end = offset
            ).firstOrNull()?.let {
                onClick()
            }
        })
}