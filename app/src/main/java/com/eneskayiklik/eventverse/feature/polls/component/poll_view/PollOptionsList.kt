package com.eneskayiklik.eventverse.feature.polls.component.poll_view

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PollOptionsList(
    userAnswer: Int,
    showAnswers: Boolean,
    percentages: List<Int>,
    options: List<String>,
    onOptionSelected: (Int) -> Unit
) {
    val primaryColor = MaterialTheme.colors.primary
    Column(verticalArrangement = Arrangement.spacedBy(5.dp), modifier = Modifier.fillMaxWidth()) {
        options.forEachIndexed { index, title ->
            val percentage = percentages[index]
            val animatedPercentage =
                animateIntAsState(
                    targetValue = if (showAnswers) percentage else 0,
                    animationSpec = tween(durationMillis = 500)
                ).value
            // Alignment animation using Bias values @see https://stackoverflow.com/a/70031663/13447094
            val gravityAnim = animateFloatAsState(
                targetValue = if (showAnswers) -1F else 0F,
                animationSpec = tween(durationMillis = 200)
            ).value
            val scaleAnim = animateFloatAsState(
                targetValue = if (showAnswers) 1F else 0F,
                animationSpec = tween(durationMillis = 200)
            ).value
            val textColor = animateColorAsState(
                targetValue = if (showAnswers) MaterialTheme.colors.onSecondary else MaterialTheme.colors.primary,
                animationSpec = tween(durationMillis = 350)
            ).value
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .border(
                        1.dp,
                        MaterialTheme.colors.primary.copy(alpha = .2F),
                        RoundedCornerShape(5.dp)
                    )
                    .clip(RoundedCornerShape(5.dp))
                    .clickable { onOptionSelected(index) }
                    .drawBehind {
                        optionViewProgress(
                            percentage = animatedPercentage,
                            color = primaryColor,
                            height = 44.dp.toPx()
                        )
                    }
                    .padding(horizontal = 10.dp)
            ) {
                Text(
                    text = title, style = MaterialTheme.typography.button.copy(
                        color = textColor,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    ), modifier = Modifier.align(BiasAlignment(gravityAnim, 0F))
                )
                Text(
                    text = "${percentages[index]}%", style = MaterialTheme.typography.button.copy(
                        MaterialTheme.colors.onSecondary, fontSize = 18.sp
                    ), modifier = Modifier
                        .scale(scaleAnim)
                        .align(Alignment.CenterEnd)
                )
            }
        }
    }
}

fun DrawScope.optionViewProgress(
    percentage: Int,
    color: Color,
    height: Float,
) {
    drawRect(color = color, size = Size(size.width * percentage / 100, height))
}