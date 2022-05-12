package com.eneskayiklik.eventverse.feature.profile.settings.edit_profile.component.department

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.eneskayiklik.eventverse.data.model.auth.Department

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DepartmentPopup(
    viewModel: DepartmentViewModel = hiltViewModel(),
    onDismissRequest: (Department) -> Unit = { }
) {
    val state = viewModel.state.collectAsState().value

    Dialog(
        onDismissRequest = { onDismissRequest(Department()) },
        DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8F)
                .background(MaterialTheme.colors.background, shape = RoundedCornerShape(5.dp))
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                LazyColumn {
                    state.faculty.forEach {
                        stickyHeader {
                            Text(
                                text = it.name,
                                style = MaterialTheme.typography.h1.copy(
                                    color = MaterialTheme.colors.primary,
                                    fontSize = 14.sp
                                ),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colors.surface)
                            )
                        }
                        items(it.departments.count()) { index ->
                            Text(
                                text = it.departments[index].departmentName,
                                style = MaterialTheme.typography.h2.copy(
                                    color = MaterialTheme.colors.onBackground,
                                    fontSize = 16.sp
                                ),
                                textAlign = TextAlign.Start,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onDismissRequest(it.departments[index])
                                    }
                                    .padding(5.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}