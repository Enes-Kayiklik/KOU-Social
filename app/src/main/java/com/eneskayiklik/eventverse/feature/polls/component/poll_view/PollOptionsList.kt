package com.eneskayiklik.eventverse.feature.polls.component.poll_view

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
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

@OptIn(
    ExperimentalAnimationApi::class
)
@Composable
fun PollOptionsList(
    userAnswer: Int,
    showAnswers: Boolean,
    percentages: List<Int>,
    options: List<String>,
    onOptionSelected: (Int) -> Unit
) {
    val primaryColor = MaterialTheme.colors.primary
    val onPrimaryColor = MaterialTheme.colors.onPrimary
    Column(verticalArrangement = Arrangement.spacedBy(5.dp), modifier = Modifier.fillMaxWidth()) {
        options.forEachIndexed { index, title ->
            var canvasWidth by remember { mutableStateOf(0F) }
            val percentage = percentages[index]
            val animatedPercentage =
                animateFloatAsState(
                    targetValue = if (showAnswers) canvasWidth * percentage / 100 else 0F,
                    animationSpec = tween(durationMillis = 700)
                ).value
            // Alignment animation using Bias values @see https://stackoverflow.com/a/70031663/13447094
            val gravityAnim = animateFloatAsState(
                targetValue = if (showAnswers) -1F else 0F,
                animationSpec = tween(durationMillis = 350)
            ).value
            // Scale anim of percentage text
            val scaleAnim = animateFloatAsState(
                targetValue = if (showAnswers) 1F else 0F,
                animationSpec = tween(durationMillis = 350)
            ).value
            val percentageText = animateIntAsState(
                targetValue = if (showAnswers) percentages[index] else 0,
                animationSpec = tween(durationMillis = 350)
            ).value
            val textColor = animateColorAsState(
                targetValue = if (showAnswers) MaterialTheme.colors.onPrimary else MaterialTheme.colors.primary,
                animationSpec = tween(durationMillis = 350)
            ).value
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .border(
                        1.dp,
                        MaterialTheme.colors.primary.copy(alpha = .2F),
                        RoundedCornerShape(5.dp)
                    )
                    .clip(RoundedCornerShape(5.dp))
                    .background(MaterialTheme.colors.primary.copy(alpha = .3F))
                    .clickable { onOptionSelected(index) }
                    .drawBehind {
                        canvasWidth = size.width
                        optionViewProgress(
                            width = animatedPercentage,
                            color = primaryColor,
                            height = 46.dp.toPx()
                        )
                    }
                    .padding(horizontal = 10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .align(BiasAlignment(gravityAnim, 0F))
                        .animateContentSize(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AnimatedVisibility(
                        visible = userAnswer == index,
                        enter = scaleIn(),
                        exit = scaleOut()
                    ) {
                        Canvas(
                            modifier = Modifier
                                .size(10.dp)
                        ) {
                            drawCircle(onPrimaryColor)
                        }
                    }
                    Text(
                        text = title, style = MaterialTheme.typography.button.copy(
                            color = textColor,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                }
                Text(
                    text = "$percentageText%", style = MaterialTheme.typography.button.copy(
                        MaterialTheme.colors.onPrimary, fontSize = 20.sp
                    ), modifier = Modifier
                        .scale(scaleAnim)
                        .align(Alignment.CenterEnd)
                )
            }
        }
    }
}

fun DrawScope.optionViewProgress(
    color: Color,
    width: Float,
    height: Float,
) {
    drawRect(color = color, size = Size(width, height))
}