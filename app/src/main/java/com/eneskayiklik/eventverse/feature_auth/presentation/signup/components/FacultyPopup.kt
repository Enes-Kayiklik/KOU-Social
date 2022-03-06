package com.eneskayiklik.eventverse.feature_auth.presentation.signup.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.eneskayiklik.eventverse.feature_auth.domain.model.Department
import com.eneskayiklik.eventverse.feature_auth.domain.model.Faculty

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FacultyPopup(faculties: List<Faculty>, onDismissRequest: (Department) -> Unit = { }) {
    var isAnimPlayed by remember { mutableStateOf(false) }
    val initialAnim by animateFloatAsState(
        targetValue = if (isAnimPlayed.not()) 0.90F else 1F,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    LaunchedEffect(key1 = Unit) {
        isAnimPlayed = true
    }
    Dialog(
        onDismissRequest = { onDismissRequest(Department()) },
        DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8F)
                .scale(initialAnim)
                .background(Color.White, shape = RoundedCornerShape(5.dp))
                .padding(10.dp)
        ) {
            LazyColumn {
                faculties.forEach {
                    stickyHeader {
                        Text(
                            text = it.name,
                            style = MaterialTheme.typography.h2,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth().background(Color.White)
                        )
                    }
                    items(it.departments.count()) { index ->
                        Text(
                            text = it.departments[index].departmentName,
                            style = MaterialTheme.typography.h1,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.fillMaxWidth().clickable {
                                onDismissRequest(it.departments[index])
                            }.padding(5.dp)
                        )
                    }
                }
            }
        }
    }
}