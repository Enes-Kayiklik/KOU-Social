package com.eneskayiklik.eventverse.feature_settings.presentation.edit_profile.component.department

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.eneskayiklik.eventverse.feature_auth.domain.model.Department

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
                .background(Color.White, shape = RoundedCornerShape(5.dp))
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
                                style = MaterialTheme.typography.h2,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White)
                            )
                        }
                        items(it.departments.count()) { index ->
                            Text(
                                text = it.departments[index].departmentName,
                                style = MaterialTheme.typography.h1,
                                textAlign = TextAlign.Start,
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